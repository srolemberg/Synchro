package br.com.samirrolemberg.synchro.util;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import br.com.samirrolemberg.synchro.R;
import br.com.samirrolemberg.synchro.receiver.ReceiverAdicionarFeed;
import br.com.samirrolemberg.synchro.receiver.ReceiverLimparConteudoFeed;

/**
 * Created by samir on 05/02/2015.
 */
public class C extends Application{
    private static Context context;

    public static ReceiverAdicionarFeed receiverAdicionarFeed;
    public static ReceiverLimparConteudoFeed receiverLimparConteudoFeed;

    public final static int NOTIFICATION_ID_ADICIONAR_FEED = 100;
    public final static int NOTIFICATION_ID_LIMPAR_CONTEUDO_FEED = 200;

    public static Context getContext(){
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context=this;
        receiverAdicionarFeed = new ReceiverAdicionarFeed();
        receiverLimparConteudoFeed = new ReceiverLimparConteudoFeed();
    }

    public static String getUnique(String c_){
        return getContext().getString(R.string.c_APP)+c_;
    }

    public static SharedPreferences getPreferences(String string){
        return getContext().getSharedPreferences(getUnique(string), C.getContext().MODE_PRIVATE);
    }
}
