package br.com.samirrolemberg.synchro.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import br.com.samirrolemberg.synchro.R;
import br.com.samirrolemberg.synchro.model.Feed;
import br.com.samirrolemberg.synchro.service.SalvarFeedService;
import br.com.samirrolemberg.synchro.util.C;

public class GlobalReceiver extends BroadcastReceiver {
    public GlobalReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        //Log.w("GlobalReceiver","Not yet implemented");
        //Toast.makeText(C.getContext(), "Not yet implemented", Toast.LENGTH_LONG).show();
        //throw new UnsupportedOperationException("Not yet implemented");
        if (intent!=null){
            if (intent.getAction().equals(C.getContext().getString(R.string.r_AdicionarFeed))){
                Intent service = new Intent(C.getContext(), SalvarFeedService.class);
                service.putExtra(C.getContext().getString(R.string.m_Feed), (Feed) intent.getExtras().get(C.getContext().getString(R.string.m_Feed)));
                service.putExtra(C.getContext().getString(R.string.m_idFeed),((Long) intent.getExtras().get(C.getContext().getString(R.string.m_idFeed))).longValue());
                C.getContext().startService(service);
                //Feed f = (Feed) intent.getExtras().get(C.getContext().getString(R.string.m_Feed));
                //Toast.makeText(C.getContext(), f.getTitulo(), Toast.LENGTH_LONG).show();
            }
        }
    }
}
