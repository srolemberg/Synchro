package br.com.samirrolemberg.synchro.service;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;

import br.com.samirrolemberg.synchro.R;
import br.com.samirrolemberg.synchro.model.Feed;
import br.com.samirrolemberg.synchro.tasks.SalvarFeedTask;
import br.com.samirrolemberg.synchro.util.C;

public class SalvarFeedService extends Service {
    private SalvarFeedTask task;

    private Feed result;
    private long idFeed;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        C.getContext().unregisterReceiver(C.receiverAdicionarFeed);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.w("MY-SERVICES", "Salvar Feed Service Start");
        result = (Feed) intent.getExtras().get(C.getContext().getString(R.string.m_Feed));
        idFeed = ((Long) intent.getExtras().get(C.getContext().getString(R.string.m_idFeed))).longValue();

        //repassa o id do novo feed vazio que sera exibido na outra tela! não poderá acessar até que a task mude a flag
        //task = new SalvarFeedTask(C.getContext(), this, intent, result, idFeed);
        task = new SalvarFeedTask(this, intent, result, idFeed);
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, ""); //executa a tarefa
        return START_REDELIVER_INTENT;
    }
}
