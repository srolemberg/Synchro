package br.com.samirrolemberg.synchro.tasks;


import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiConfiguration;
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
 * Created by samir on 09/02/2015.
 */
public class AdicionarFeedVolleyTask implements Response.Listener<String>, Response.ErrorListener {

    private Context context;
    private AdicionarFeedDelegate delegate;

    private SyndFeed feed = null;
    //private ExceptionMessage exm;
    private RequestQueue queue = null;
    private SyndFeedInput input;
    private StringRequest stringRequest;
    private String url;

    public String status;

    public AdicionarFeedVolleyTask(Context context, AdicionarFeedDelegate delegate){
        super();
        status = C.getContext().getString(R.string.INICIANDO);
        this.context = context;
        this.delegate = delegate;
        this.input = new SyndFeedInput();
        this.feed = null;
        this.queue = Volley.newRequestQueue(context);//init request queue
        this.delegate.onPreExecute();
        this.url = null;
    }

    public void doRequest(int request_method, String... params){
        url = params[0];
        //request a string response from url
        stringRequest = new StringRequest(request_method, url, this,this);
        stringRequest.setTag(C.getUnique(C.getContext().getString(R.string.c_ADICIONAR_FEED)));
        queue.add(stringRequest);
        status = C.getContext().getString(R.string.EXECUTANDO);
    }

    public void cancel(){
        Log.i("teste", "cancel");
        queue.cancelAll(stringRequest.getTag());//cancela uma requisição que não foi entregue
        status = C.getContext().getString(R.string.FINALIZADO);
    }
    @Override
    public void onResponse(String response) {
        try {
            feed = input.build(new StringReader(response));
        } catch (IllegalArgumentException e) {
            Log.e("ADDFEED", "IllegalArgumentException");
            Log.e("ADDFEED", e.getMessage(),e);
            status = C.getContext().getString(R.string.FINALIZADO);
            delegate.onError(e.getMessage());
        } catch (FeedException e) {//XMl invpalido - nenhum elemento encontrado
            Log.e("ADDFEED", "FeedException");
            Log.e("ADDFEED", e.getMessage(),e);
            status = C.getContext().getString(R.string.FINALIZADO);
            delegate.onError(e.getMessage());
        }  catch (Exception e) {
            Log.e("ADDFEED", "Exception");
            Log.e("ADDFEED", e.getMessage(),e);
            status = C.getContext().getString(R.string.FINALIZADO);
            delegate.onError(e.getMessage());
        }
        if (!status.equals(C.getContext().getString(R.string.FINALIZADO))){//se não foi cancelado, não consome
            Feed f = SimpleFeed.consumir(feed, url);
            if (!status.equals(C.getContext().getString(R.string.FINALIZADO))){//se não foi cancelado, não entrega
                delegate.onPostExecute(f);
            }
        }
        status = C.getContext().getString(R.string.FINALIZADO);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.e("response", error.getMessage());
        delegate.onError(error.getMessage());
        status = C.getContext().getString(R.string.FINALIZADO);
   }
}
