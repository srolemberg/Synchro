package br.com.samirrolemberg.synchro.fragment;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import br.com.samirrolemberg.synchro.R;
import br.com.samirrolemberg.synchro.activity.AdicionarFeedActivity;
import br.com.samirrolemberg.synchro.delegate.AdicionarFeedDelegate;
import br.com.samirrolemberg.synchro.model.Categoria;
import br.com.samirrolemberg.synchro.model.Feed;
import br.com.samirrolemberg.synchro.tasks.AdicionarFeedTask;
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

    private AdicionarFeedTask task;
    private String unique = C.c_APP+C.c_ADICIONAR_FEED;
    private Feed feed;

    ProgressDialog pDialog;

    private BroadcastReceiver bAdicionarFeed = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getSerializableExtra(C.c_ADICIONAR_FEED)!=null){
                feed = (Feed) intent.getSerializableExtra(C.c_ADICIONAR_FEED);
                show(context, feed);
            }
        }
    };

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
                    task = new AdicionarFeedTask(getActivity(), AdicionarFeedFragment.this);
                    String params[] = {
                            url.getText().toString()
                    };
                    task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, params);
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
                    if (task.getStatus().equals(AsyncTask.Status.RUNNING)||task.getStatus().equals(AsyncTask.Status.PENDING)){
                        task.cancel(true);
                    }
                }
            }
        });
        pDialog.show();
    }

    @Override
    public void onPostExecute(Intent intent) {
        if (task!=null) {
            pDialog.dismiss();
        }
        layout.setVisibility(View.VISIBLE);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (task!=null) {
            if (task.getStatus().equals(AsyncTask.Status.RUNNING) || task.getStatus().equals(AsyncTask.Status.PENDING)) {
                //pDialog = ProgressDialog.show(getActivity(), "", "", true);
                pDialog.show();
            }
        }
    }

    @Override
    public void onDetach() {
        if (pDialog!=null&&pDialog.isShowing()){
            pDialog.dismiss();
        }
        super.onDetach();
    }
}