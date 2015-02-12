package br.com.samirrolemberg.synchro.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

import br.com.samirrolemberg.synchro.R;
import br.com.samirrolemberg.synchro.delegate.AdicionarFeedDelegate;
import br.com.samirrolemberg.synchro.model.Categoria;
import br.com.samirrolemberg.synchro.model.Feed;
import br.com.samirrolemberg.synchro.tasks.AdicionarFeedVolleyTask;
import br.com.samirrolemberg.synchro.util.C;

/**
 * Created by samir on 06/02/2015.
 */
public class AdicionarFeedFragment extends Fragment implements AdicionarFeedDelegate {

    public AdicionarFeedFragment() {
    }

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
        if (savedInstanceState!=null){
            Log.i("outState","dessalvando");
            feed = (Feed) savedInstanceState.getSerializable("feed");
            setLayoutValues(feed);
        }
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

        //ouvir componentes
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        url.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (URLUtil.isValidUrl(url.getText().toString())) {//se a url é valida
                    layout.setVisibility(View.GONE);
                    task = new AdicionarFeedVolleyTask(getActivity(), AdicionarFeedFragment.this);
                    String params[] = {
                            url.getText().toString()
                    };
                    task.doRequest(Request.Method.GET, params);
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
        Log.i("outState","salvando");
        outState.putSerializable("feed",feed);
        super.onSaveInstanceState(outState);

        SharedPreferences preferences = C.preferences(C.getContext().getString(R.string.c_ADICIONAR_FEED));


    }
}