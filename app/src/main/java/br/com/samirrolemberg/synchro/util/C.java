package br.com.samirrolemberg.synchro.util;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import br.com.samirrolemberg.synchro.R;

/**
 * Created by samir on 05/02/2015.
 */
public class C extends Application{


    private static Context context;

    public static Context getContext(){
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context=this;
    }

    public static String getUnique(String c_){
        return getContext().getString(R.string.c_APP)+c_;
    }

    public static SharedPreferences preferences(String string){
        return getContext().getSharedPreferences(getUnique(string), C.getContext().MODE_PRIVATE);
    }
}
