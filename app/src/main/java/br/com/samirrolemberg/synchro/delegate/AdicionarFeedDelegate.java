package br.com.samirrolemberg.synchro.delegate;

import android.content.Intent;

import br.com.samirrolemberg.synchro.model.Feed;

/**
 * Created by samir on 06/02/2015.
 */
public interface AdicionarFeedDelegate {

    void onPreExecute();
    void onPostExecute(Intent intent);

}
