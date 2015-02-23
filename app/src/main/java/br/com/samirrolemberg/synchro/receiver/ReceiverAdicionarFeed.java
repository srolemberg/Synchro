package br.com.samirrolemberg.synchro.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import br.com.samirrolemberg.synchro.R;
import br.com.samirrolemberg.synchro.model.Feed;
import br.com.samirrolemberg.synchro.service.SalvarFeedService;
import br.com.samirrolemberg.synchro.util.C;
import br.com.samirrolemberg.synchro.util.U;

public class ReceiverAdicionarFeed extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent!=null){
            if (intent.getAction().equals(C.getContext().getString(R.string.r_AdicionarFeed))){
                if (!U.isMyServiceRunning(SalvarFeedService.class, C.getContext())){
                    Intent service = new Intent(C.getContext(), SalvarFeedService.class);
                    service.putExtra(C.getContext().getString(R.string.m_Feed), (Feed) intent.getExtras().get(C.getContext().getString(R.string.m_Feed)));
                    service.putExtra(C.getContext().getString(R.string.m_idFeed),((Long) intent.getExtras().get(C.getContext().getString(R.string.m_idFeed))).longValue());
                    C.getContext().startService(service);
                    Toast.makeText(C.getContext(), "ReceiverAdicionarFeed", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}
