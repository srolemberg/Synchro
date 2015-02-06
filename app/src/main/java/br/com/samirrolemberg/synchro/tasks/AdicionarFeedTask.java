package br.com.samirrolemberg.synchro.tasks;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.code.rome.android.repackaged.com.sun.syndication.feed.synd.SyndFeed;
import com.google.code.rome.android.repackaged.com.sun.syndication.io.FeedException;
import com.google.code.rome.android.repackaged.com.sun.syndication.io.SyndFeedInput;

import java.io.StringReader;

import br.com.samirrolemberg.synchro.R;
import br.com.samirrolemberg.synchro.delegate.AdicionarFeedDelegate;
import br.com.samirrolemberg.synchro.model.ExceptionMessage;
import br.com.samirrolemberg.synchro.model.Feed;
import br.com.samirrolemberg.synchro.model.SimpleFeed;
import br.com.samirrolemberg.synchro.util.C;

/**
 * Created by samir on 04/02/2015.
 */
public class AdicionarFeedTask extends AsyncTask<String, Integer, Void> {

    public AdicionarFeedDelegate delegate;

    private Context context;

    private SyndFeed feed = null;
    private ExceptionMessage exm;
    private RequestQueue queue = null;
    private SyndFeedInput input;

    public AdicionarFeedTask(Context context, AdicionarFeedDelegate delegate){
        super();
        this.context = context;
        this.delegate = delegate;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        input = new SyndFeedInput();
        feed = null;
        queue = Volley.newRequestQueue(context);//init request queue
        delegate.onPreExecute();
    }

    @Override
    protected Void doInBackground(String... params) {
        final String url = params[0];
        //request a string response from url
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
           new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                try {
                    Log.e("ADDFEED", "IllegalArgumentException");
                    feed = input.build(new StringReader(response));
                    Log.e("ADDFEED", "IllegalArgumentException");
                } catch (IllegalArgumentException e) {
                    Log.e("ADDFEED", "IllegalArgumentException");
                    Log.e("ADDFEED", e.getMessage(),e);
                    Log.e("ADDFEED", e.getLocalizedMessage(),e);
                    exm = new ExceptionMessage(e);
                } catch (FeedException e) {//XMl invpalido - nenhum elemento encontrado
                    Log.e("ADDFEED", "FeedException");
                    Log.e("ADDFEED", e.getMessage(),e);
                    Log.e("ADDFEED", e.getLocalizedMessage(),e);
                    exm = new ExceptionMessage(e);
                }  catch (Exception e) {
                    Log.e("ADDFEED", "Exception");
                    Log.e("ADDFEED", e.getMessage(),e);
                    Log.e("ADDFEED", e.getLocalizedMessage(),e);
                    exm = new ExceptionMessage(e);
                }
                Intent mensagem = new Intent(C.getUnique(C.c_ADICIONAR_FEED));
                Feed f = SimpleFeed.consumir(feed,url);
                mensagem.putExtra(C.c_ADICIONAR_FEED, f);
                delegate.onPostExecute(mensagem);
               // LocalBroadcastManager.getInstance(context).sendBroadcast(mensagem);
               // Log.e("ADDFEED", "Feed: "+f.getTitulo());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               // Log.e("ADDFEED", "VolleyError");
               //Log.e("ADDFEED", error.getMessage(),error);
               //Log.e("ADDFEED", error.getLocalizedMessage(),error);
                exm = new ExceptionMessage(error);

                Intent mensagem = new Intent(C.getUnique(C.c_ADICIONAR_FEED));
                mensagem.putExtra(C.c_ADICIONAR_FEED, error.getMessage());
                delegate.onPostExecute(mensagem);
                // LocalBroadcastManager.getInstance(context).sendBroadcast(mensagem);
            }
        });

        stringRequest.setTag(C.getUnique(C.c_ADICIONAR_FEED));
        queue.add(stringRequest);
        return null;
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        queue.cancelAll(C.getUnique(C.c_ADICIONAR_FEED));
    }
}
