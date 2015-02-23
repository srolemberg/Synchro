package br.com.samirrolemberg.synchro.fragment;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;

import br.com.samirrolemberg.synchro.R;
import br.com.samirrolemberg.synchro.conn.DatabaseManager;
import br.com.samirrolemberg.synchro.dao.DAOConteudo;
import br.com.samirrolemberg.synchro.dao.DAODescricao;
import br.com.samirrolemberg.synchro.model.Conteudo;
import br.com.samirrolemberg.synchro.model.Descricao;
import br.com.samirrolemberg.synchro.model.Post;
import br.com.samirrolemberg.synchro.util.C;
import br.com.samirrolemberg.synchro.util.U;

public class LerPostFragment extends Fragment {

    private Post postAux = null;

    private List<Conteudo> conteudosFrag = null;
    private List<Descricao> descricoesFrag = null;

    private DAOConteudo daoConteudo = null;
    private DAODescricao daoDescricao = null;

    private WebView navegador = null;

    public final static String _button = "_BUTTON";
    public final static String _img = "_IMG";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        postAux = (Post) getActivity().getIntent().getExtras().get(C.getContext().getString(R.string.m_Post));

        daoConteudo = new DAOConteudo(getActivity());
        daoDescricao = new DAODescricao(getActivity());

        conteudosFrag = daoConteudo.listarTudo(postAux);
        descricoesFrag = daoDescricao.listarTudo(postAux);

        DatabaseManager.getInstance().closeDatabase();
        DatabaseManager.getInstance().closeDatabase();
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ler_post, container, false);
        navegador = (WebView) rootView.findViewById(R.id.webView_ler_post);

        daoConteudo = new DAOConteudo(getActivity());
        daoDescricao = new DAODescricao(getActivity());

        config(navegador);

        if (daoConteudo.size(postAux)>0) {//se o conteudo total não é nulo
            String data = conteudosFrag.get(0).getValor();
            String mimeType = "text/html";
            data = data.replace("src=\"//", "src=\"http://");

            data = returnData(data);

            navegador.loadData(data, mimeType+"; charset=UTF-8",null);

        }else if (daoDescricao.size(postAux)>0){//se a não há conteudo mas há descrição
            String data = descricoesFrag.get(0).getValor();
            data = data.replace("src=\"//", "src=\"http://");

            String mimeType = "text/html";

            data = returnData(data);

            navegador.loadData(data, mimeType+"; charset=UTF-8",null);

        }
        DatabaseManager.getInstance().closeDatabase();
        DatabaseManager.getInstance().closeDatabase();

        setRetainInstance(true);
        return rootView;
    }

    private class JSInterface {
        private Activity activity;
        @SuppressWarnings("unused")
        private JSInterface() {
            new Exception();
        }
        public JSInterface(Activity activity){
            this.activity = activity;
        }
        @JavascriptInterface
        public void toast(String toast){
            Toast.makeText(activity, toast, Toast.LENGTH_SHORT).show();
        }

        @SuppressLint("InflateParams")
        @JavascriptInterface
        public void abrir(String url, final String tipo){
            String frase = "";
            if (tipo.equals(_button)) {
                frase = "Como deseja abrir o vídeo?";
            }else
            if (tipo.equals(_img)){
                frase = "Como deseja abrir a imagem?";
            }

            LayoutInflater inflater = activity.getLayoutInflater();
            //View pergunta = (new DetalhesPerguntaDialogPost(activity,  inflater.inflate(R.layout.dialog_pergunta_feed, null), postAux,frase)).create();
            final Uri uri = Uri.parse(url);
            final Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            new AlertDialog.Builder(activity)
                    //.setView(pergunta)
                    .setTitle("Atenção")
                    .setMessage(frase)
                    .setPositiveButton("Navegador", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            open(intent);
                        }
                    })
                    .setNegativeButton("Aplicativo", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (tipo.equals(_button)) {
                                intent.setDataAndType(uri, "video/*");
                            }else
                            if (tipo.equals(_img)) {
                                intent.setDataAndType(uri, "image/*");
                            }
                            open(intent);
                        }
                    })
                    .show();
        }

        private void open(final Intent intent){
            if (U.isConnected(activity)) {
                startActivity(intent);
            }else{
                toast("Não há conexão de internet.");
            }
        }

    }
    @SuppressLint("SetJavaScriptEnabled")
    private void config(final WebView view){
        view.getSettings().setBlockNetworkLoads(false);
        view.getSettings().setJavaScriptEnabled(true);
        view.getSettings().setLoadWithOverviewMode(true);
        view.getSettings().setUseWideViewPort(true);
        view.setWebChromeClient(new WebChromeClient());
        view.addJavascriptInterface(new JSInterface(getActivity()), "Android");
    }

    private String returnData(String data){
        int vertical = Configuration.ORIENTATION_PORTRAIT;
        int orientacao = getResources().getConfiguration().orientation;

        Document doc = Jsoup.parse(data);

        Elements eA = doc.select("a");

        for (Element a : eA) {
            if (!a.getElementsByTag("img").isEmpty()) {//se algum link tem imagem
                Elements eImg = a.getElementsByTag("img");//pega todas as imgs
                for (Element img : eImg) {//para cada img de cada a
                    if (img.hasAttr("src")) {
                        //a.attr("class",CLASS_LINKHITOF);
                        a.removeAttr("href");
                    }
                }
            }
        }
        Elements eDiv = doc.select("div");
        eDiv.removeAttr("style");

        for (Element div : eDiv) {//tratamento para alguma plugin de site que trabalha com feedburner
            if (div.className().equalsIgnoreCase("feedflare")) {
                div.remove();
            }
        }

        Elements eImg = doc.select("img");
        eImg.wrap("<p>");//corrige a quebra de linha de alguns feeds
        eImg.wrap("<center>");//centraliza a imagem
        eImg.removeAttr("width");//remove altura e largura e alinhamento e deixa o css tratar
        eImg.removeAttr("height");
        eImg.removeAttr("align");//cada img possui um center e um p

        for (Element img : eImg) {
            String src = img.getElementsByTag("img").attr("src");
            img.attr("onClick", "abrir('"+src+"','"+_img+"')");//seta onclick para todos os imgs
            //TODO: REVER AS TAGS LINKS QUE FAZEM DUPLO CLICK EM IMAGENS
        }

        Element eHead = doc.head();
        String script1 = "<script type=\"text/javascript\">"+
                "function abrir(toast, type) {"+
                "    Android.abrir(toast, type);"+
                "}"+
                "</script>";
        String style = "<style type=\"text/css\">"

                +"button{"
                +" display:inline;"
                +" height:auto;"
                //+" max-width:100%;"
                +((orientacao==vertical)?" max-width:100%;":" max-width:55%;")
                //+" font-size:40px;"
                +((orientacao==vertical)?" font-size:50px;":" font-size:30px;")
                + "}"
                +"img{"
                +" display:inline;"
                +" height:auto;"
                //+" max-width:100%;"
                +((orientacao==vertical)?" max-width:100%;":" max-width:55%;")
                +" align:middle;"
                + "}"
                +"body{"
                //+" font-size:40px;"
                +((orientacao==vertical)?" font-size:50px;":" font-size:30px;")
                +"text-align: justify;"
                +"text-justify: inter-word;"
                + "}"
                +"</style>";
        eHead.append(script1);
        eHead.append(style);


        Elements eIframe = doc.select("iframe");
        eIframe.tagName("button");
        eIframe.wrap("<center>");
        int i = 0;
        for (Element button : eIframe ) {
            String src = button.getElementsByTag("button").attr("src");
            for (Attribute attr : button.attributes().asList()) {
                button.removeAttr(attr.getKey());
            }
            button.attr("onClick", "abrir('"+src+"', '"+_button+"')");
            button.text("Abrir conteúdo multi-mídia "+(++i));
        }

        return data = doc.html();
    }


}
