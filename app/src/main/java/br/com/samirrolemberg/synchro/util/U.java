package br.com.samirrolemberg.synchro.util;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.net.ConnectivityManager;
import android.text.format.DateFormat;
import android.util.Log;

import java.util.Date;

public class U {

	private U() {}
	
	public static boolean isMyServiceRunning(Class<?> serviceClass, Context context) {
	    ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
	    for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
	        if (serviceClass.getName().equals(service.service.getClassName())) {
	            return true;
	        }
	    }
	    return false;
	}
	
	public static boolean isConnected(Context  context) {
        try {
            ConnectivityManager cm = (ConnectivityManager)
            context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnected()) {
                    Log.d("LAN-STATE","Status de conexão 3G: "+cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnected());
                    return true;
            } else if(cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected()){
                    Log.d("LAN-STATE","Status de conexão Wifi: "+cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected());
                    return true;
            } else {
                    Log.e("LAN-STATE","Status de conexão Wifi: "+cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected());
                    Log.e("LAN-STATE","Status de conexão 3G: "+cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnected());
                    return false;
            }
        } catch (Exception  e) {
                Log.e("LAN-STATE",e.getMessage());
                return false;
        }
    }

	public static CharSequence date_time_24_mask(Date date, Context context){
		CharSequence data = date_mask(date, context);
		CharSequence time = time_24_mask(date, context);
		String conv = time.toString()+" "+data.toString();
		return conv;
	}
	public static CharSequence time_24_date_mask(Date date, Context context){
		CharSequence data = date_mask(date, context);
		CharSequence time = time_24_mask(date, context);
		String conv = data.toString()+" "+time.toString();
		return conv;
	}
	
	public static CharSequence time_24_mask(Date date, Context context){
		java.text.DateFormat d = DateFormat.getTimeFormat(context);
		return d.format(date);
	}
	public static CharSequence date_mask(Date date, Context context){
		java.text.DateFormat d = DateFormat.getDateFormat(context);
		return d.format(date);
	}
}
