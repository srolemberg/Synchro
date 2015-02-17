package br.com.samirrolemberg.synchro.service;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;

import br.com.samirrolemberg.synchro.R;
import br.com.samirrolemberg.synchro.model.Feed;
import br.com.samirrolemberg.synchro.tasks.LimparConteudoFeedTask;
import br.com.samirrolemberg.synchro.util.C;

public class LimparConteudoFeedService extends Service {
    private LimparConteudoFeedTask task;

    private Feed result;
    private long idFeed;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        C.getContext().unregisterReceiver(C.receiverLimparConteudoFeed);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.w("MY-SERVICES", "Limpar Conteudo Feed Service Start");
        result = (Feed) intent.getExtras().get(C.getContext().getString(R.string.m_Feed));
        idFeed = ((Long) intent.getExtras().get(C.getContext().getString(R.string.m_idFeed))).longValue();

        task = new LimparConteudoFeedTask(this, intent, result, idFeed);
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, ""); //executa a tarefa
        return START_REDELIVER_INTENT;
    }
}
