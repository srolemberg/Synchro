package br.com.samirrolemberg.synchro.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import br.com.samirrolemberg.synchro.R;
import br.com.samirrolemberg.synchro.model.Feed;
import br.com.samirrolemberg.synchro.service.LimparConteudoFeedService;
import br.com.samirrolemberg.synchro.util.C;
import br.com.samirrolemberg.synchro.util.U;

public class ReceiverLimparConteudoFeed extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent!=null){
            if (intent.getAction().equals(C.getContext().getString(R.string.r_LimparConteudoFeed))){
                if (!U.isMyServiceRunning(LimparConteudoFeedService.class, C.getContext())){
                    Intent service = new Intent(C.getContext(), LimparConteudoFeedService.class);
                    service.putExtra(C.getContext().getString(R.string.m_Feed), (Feed) intent.getExtras().get(C.getContext().getString(R.string.m_Feed)));
                    service.putExtra(C.getContext().getString(R.string.m_idFeed),((Long) intent.getExtras().get(C.getContext().getString(R.string.m_idFeed))).longValue());
                    C.getContext().startService(service);
                }
            }
        }
    }
}
