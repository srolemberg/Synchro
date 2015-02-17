package br.com.samirrolemberg.synchro.util;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import br.com.samirrolemberg.synchro.R;
import br.com.samirrolemberg.synchro.receiver.GlobalReceiver;

/**
 * Created by samir on 05/02/2015.
 */
public class C extends Application{
    private static Context context;
    public static GlobalReceiver globalReceiver;

    public final static int NOTIFICATION_ID_ADICIONAR_FEED = 100;

    public static Context getContext(){
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context=this;
        globalReceiver = new GlobalReceiver();
    }

    public static String getUnique(String c_){
        return getContext().getString(R.string.c_APP)+c_;
    }

    public static SharedPreferences getPreferences(String string){
        return getContext().getSharedPreferences(getUnique(string), C.getContext().MODE_PRIVATE);
    }
}
