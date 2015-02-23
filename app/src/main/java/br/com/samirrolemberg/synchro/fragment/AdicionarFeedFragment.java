package br.com.samirrolemberg.synchro.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.google.gson.Gson;

import br.com.samirrolemberg.synchro.R;
import br.com.samirrolemberg.synchro.conn.DatabaseManager;
import br.com.samirrolemberg.synchro.dao.DAOFeed;
import br.com.samirrolemberg.synchro.delegate.AdicionarFeedDelegate;
import br.com.samirrolemberg.synchro.model.Categoria;
import br.com.samirrolemberg.synchro.model.Feed;
import br.com.samirrolemberg.synchro.service.SalvarFeedService;
import br.com.samirrolemberg.synchro.tasks.AdicionarFeedVolleyTask;
import br.com.samirrolemberg.synchro.util.C;
import br.com.samirrolemberg.synchro.util.U;

/**
 * Created by samir on 06/02/2015.
 */
public class AdicionarFeedFragment extends Fragment implements AdicionarFeedDelegate {

    private EditText url;
    private Button add;
    private View layout;

    private TextView nome, descricao, link, idioma, categoria;

    private AdicionarFeedVolleyTask task;
    private Feed feed;

    ProgressDialog pDialog;

    private void show(Context context, Feed feed){
        layout.setVisibility(View.VISIBLE);
        nome.setText(feed.getTitulo());
        descricao.setText(feed.getDescricao());
        link.setText(feed.getLink());
        idioma.setText(feed.getIdioma());
        String cat = "";
        StringBuffer buffer = new StringBuffer();
        for (Categoria c:feed.getCategorias()){
            buffer.append(c.getNome()+"|");
        }
        cat=buffer.toString();
        categoria.setText(cat);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        Log.i("outState","create");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_adicionar_feed, container, false);

        //associar componentes
        url = (EditText) rootView.findViewById(R.id.url_adicionar_feed);
        add = (Button) rootView.findViewById(R.id.adicionar_adicionar_feed);

        layout      = (View)     rootView.findViewById(R.id.layout_adicionar_feed);
        nome        = (TextView) rootView.findViewById(R.id.nome_adicionar_feed);
        descricao   = (TextView) rootView.findViewById(R.id.descricao_adicionar_feed);
        link        = (TextView) rootView.findViewById(R.id.link_adicionar_feed);
        idioma      = (TextView) rootView.findViewById(R.id.idioma_adicionar_feed);
        categoria   = (TextView) rootView.findViewById(R.id.categoria_adicionar_feed);

        SharedPreferences preferences = C.getPreferences(C.getContext().getString(R.string.c_ADICIONAR_FEED));
        if (!preferences.getString(C.getContext().getString(R.string.FEED_CACHE),"").isEmpty()){
            Log.i("outState","recreate");
            String b64 = preferences.getString(C.getContext().getString(R.string.FEED_CACHE),"");
            byte[] bytes = Base64.decode(b64.getBytes(),Base64.URL_SAFE);
            String jSon = new String(bytes);
            Gson gSon = new Gson();
            this.feed = gSon.fromJson(jSon, Feed.class);
            setLayoutValues(feed);
        }

        //getActivity().registerForContextMenu(dialog_sobre);

        //ouvir componentes
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!U.isMyServiceRunning(SalvarFeedService.class,C.getContext())){
                    DAOFeed daoFeed = new DAOFeed(C.getContext());
                    long idFeed = daoFeed.inserir(feed);
                    DatabaseManager.getInstance().closeDatabase();

                    IntentFilter filter = new IntentFilter(C.getContext().getString(R.string.r_AdicionarFeed));
                    C.getContext().registerReceiver(C.receiverAdicionarFeed, filter);

                    Intent intent = new Intent();
                    intent.setAction(C.getContext().getString(R.string.r_AdicionarFeed));
                    intent.putExtra(C.getContext().getString(R.string.m_Feed), feed);
                    intent.putExtra(C.getContext().getString(R.string.m_idFeed,idFeed), idFeed);
                    C.getContext().sendBroadcast(intent);
                    NavUtils.navigateUpFromSameTask(getActivity());
                    //getActivity().onBackPressed();//retorna para a tela anterior
                }else{
                    Toast.makeText(C.getContext(), "A operação está ocupada no momento. Aguarde alguns instantes.", Toast.LENGTH_LONG).show();
                }
                //C.getContext().unregisterReceiver(new GlobalReceiver());
            }
        });
        url.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (URLUtil.isValidUrl(url.getText().toString())) {//se a url é valida
                    if (U.isConnected(C.getContext())){
                        layout.setVisibility(View.GONE);
                        task = new AdicionarFeedVolleyTask(getActivity(), AdicionarFeedFragment.this);
                        String params[] = {
                                url.getText().toString()
                        };
                        task.doRequest(Request.Method.GET, params);
                    }else{
                        Toast.makeText(C.getContext(), "Não há conexão com a Internet.", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "URL inválida.", Toast.LENGTH_LONG).show();
                }
                return false;
            }
        });
        return rootView;
    }

    @Override
    public void onPreExecute() {
        pDialog = new ProgressDialog(getActivity());
        pDialog.setIndeterminate(true);
        pDialog.setCancelable(true);
        pDialog.setOnCancelListener(new ProgressDialog.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                if (task!=null){
                    if (task.status.equals(C.getContext().getString(R.string.EXECUTANDO))){
                        task.cancel();
                    }
                }
            }
        });
        pDialog.show();
    }

    @Override
    public void onPostExecute(Feed feed) {
        if (task!=null) {
            pDialog.dismiss();
        }
        if (feed!=null){
            setLayoutValues(feed);
            this.feed = feed;
        }
    }

    private void setLayoutValues(Feed feed){
        nome.setText(feed.getTitulo());
        descricao.setText(feed.getDescricao());
        link.setText(feed.getRss());
        idioma.setText(feed.getIdioma());
        StringBuffer cat = new StringBuffer();
        for(Categoria c : feed.getCategorias()){
            cat.append(c.getNome()+"|");
        }
        categoria.setText(cat.toString());
        layout.setVisibility(View.VISIBLE);
    }

    @Override
    public void onError(String mensagem) {
        if (mensagem!=null){
            Toast.makeText(C.getContext(), mensagem,Toast.LENGTH_LONG).show();
            pDialog.dismiss();
            layout.setVisibility(View.GONE);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (task!=null) {
            if (task.status.equals(C.getContext().getString(R.string.EXECUTANDO))) {
                pDialog.show();
            }
        }
        if (feed!=null){
            setLayoutValues(feed);
        }
    }

    @Override
    public void onDetach() {
        if (pDialog!=null&&pDialog.isShowing()){
            pDialog.dismiss();
        }
        super.onDetach();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (feed!=null){
            Log.i("outState","salvando");
            SharedPreferences preferences = C.getPreferences(C.getContext().getString(R.string.c_ADICIONAR_FEED));
            Gson gSon = new Gson();
            String jSon = gSon.toJson(feed, Feed.class);
            byte[] b64 = Base64.encode(jSon.getBytes(),Base64.URL_SAFE);
            jSon = new String(b64);
            preferences.edit().putString(C.getContext().getString(R.string.FEED_CACHE),jSon).commit();
        }
    }
}