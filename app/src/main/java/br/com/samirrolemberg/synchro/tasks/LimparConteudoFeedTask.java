package br.com.samirrolemberg.synchro.tasks;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import br.com.samirrolemberg.synchro.R;
import br.com.samirrolemberg.synchro.conn.DatabaseManager;
import br.com.samirrolemberg.synchro.dao.DAOAnexo;
import br.com.samirrolemberg.synchro.dao.DAOCategoria;
import br.com.samirrolemberg.synchro.dao.DAOConteudo;
import br.com.samirrolemberg.synchro.dao.DAODescricao;
import br.com.samirrolemberg.synchro.dao.DAOFeed;
import br.com.samirrolemberg.synchro.dao.DAOPost;
import br.com.samirrolemberg.synchro.model.Feed;
import br.com.samirrolemberg.synchro.model.Post;
import br.com.samirrolemberg.synchro.service.LimparConteudoFeedService;
import br.com.samirrolemberg.synchro.util.C;

/**
 * Created by Samir on 14/02/2015.
 */
public class LimparConteudoFeedTask extends AsyncTask<String, Integer, Void> {

    private LimparConteudoFeedService service;
    private Intent intent;
    private Feed feed;
    private long idFeed;
    private NotificationManager mNotifyManager = null;
    private NotificationCompat.Builder mBuilder = null;

    private List<Long> idsPost = null;

    private DAOFeed daoFeed = null;
    private DAOPost daoPost = null;
    private DAODescricao daoDescricao = null;
    private DAOConteudo daoConteudo = null;
    private DAOAnexo daoAnexo = null;
    private DAOCategoria daoCategoria = null;


    public LimparConteudoFeedTask(LimparConteudoFeedService service, Intent intent, Feed feed, long idFeed){
        super();
        this.service=service;
        this.intent=intent;
        this.feed=feed;
        this.idFeed=idFeed;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //estimativa = estimativaDosFor()*2;
        mNotifyManager = (NotificationManager) C.getContext().getSystemService(Context.NOTIFICATION_SERVICE);
        mBuilder = new NotificationCompat.Builder(C.getContext())
                    .setContentTitle("Limpando " + feed.getTitulo())
                    .setContentText("Limpando o conteúdo do feed.")
                    .setOngoing(true)
                    .setSmallIcon(R.drawable.ic_action_rss_icon_bola_transparente)
                    .setProgress(0,0,true);
                    //.build();
            mNotifyManager.notify(C.NOTIFICATION_ID_LIMPAR_CONTEUDO_FEED, mBuilder.build());
    }

    @Override
    protected Void doInBackground(String... params) {
        limpar(C.getContext(), feed);
        mBuilder.setContentText("Conteúdo removido.")
                .setOngoing(false)
                .setProgress(0, 0, false);
        mNotifyManager.notify(C.NOTIFICATION_ID_LIMPAR_CONTEUDO_FEED, mBuilder.build());

        return null;
    }


    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        mBuilder.setProgress(0, 0, false)
                .setOngoing(false);
        mNotifyManager.notify(C.NOTIFICATION_ID_LIMPAR_CONTEUDO_FEED, mBuilder.build());
        Toast.makeText(C.getContext(), feed.getTitulo()+" foi limpo com sucesso.", Toast.LENGTH_SHORT).show();
        if (service!=null) {
            Log.i("MY-SERVICES", "LimparConteudoFeedTask - TRY STOP");
            this.cancel(false);
            service.stopService(intent);
        }

    }
    private void limpar(Context context, Feed feed){
        mBuilder.setProgress(0, 0, true);
        mNotifyManager.notify(C.NOTIFICATION_ID_LIMPAR_CONTEUDO_FEED, mBuilder.build());

        daoFeed = new DAOFeed(context);
        int at = daoFeed.atualizaDataPublicacao(feed);//para um futuro update

        daoPost = new DAOPost(context);
        //TODO: REFAZER NO FUTURO, apenas com os ids
        //obter todos os ids de posts
        List<Post> posts = daoPost.listarIDS(feed);
        daoAnexo = new DAOAnexo(context);
        daoCategoria = new DAOCategoria(context);
        daoConteudo = new DAOConteudo(context);
        daoDescricao = new DAODescricao(context);
        for (Post post : posts) {
            daoPost.remover(post);
            daoAnexo.remover(post);
            daoCategoria.remover(post);
            daoConteudo.remover(post);
            daoDescricao.remover(post);
        }
        DatabaseManager.getInstance().closeDatabase();
        DatabaseManager.getInstance().closeDatabase();
        DatabaseManager.getInstance().closeDatabase();
        DatabaseManager.getInstance().closeDatabase();
        DatabaseManager.getInstance().closeDatabase();
        DatabaseManager.getInstance().closeDatabase();

    }

}
