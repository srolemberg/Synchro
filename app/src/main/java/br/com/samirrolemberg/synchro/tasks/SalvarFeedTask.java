package br.com.samirrolemberg.synchro.tasks;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.samirrolemberg.synchro.R;
import br.com.samirrolemberg.synchro.conn.DatabaseManager;
import br.com.samirrolemberg.synchro.dao.DAOAnexo;
import br.com.samirrolemberg.synchro.dao.DAOCategoria;
import br.com.samirrolemberg.synchro.dao.DAOConteudo;
import br.com.samirrolemberg.synchro.dao.DAODescricao;
import br.com.samirrolemberg.synchro.dao.DAOFeed;
import br.com.samirrolemberg.synchro.dao.DAOImagem;
import br.com.samirrolemberg.synchro.dao.DAOPost;
import br.com.samirrolemberg.synchro.model.Feed;
import br.com.samirrolemberg.synchro.model.Post;
import br.com.samirrolemberg.synchro.service.SalvarFeedService;
import br.com.samirrolemberg.synchro.util.C;

/**
 * Created by Samir on 14/02/2015.
 */
public class SalvarFeedTask extends AsyncTask<String, Integer, Void> {

    private SalvarFeedService service;
    private Intent intent;
    private Feed feed;
    private long idFeed;
    private NotificationManager mNotifyManager = null;
    private NotificationCompat.Builder mBuilder = null;
    private int estimativa, atual = 0;
    private List<Long> idsPost = null;

    private DAOFeed daoFeed = null;
    private DAOPost daoPost = null;
    private DAODescricao daoDescricao = null;
    private DAOConteudo daoConteudo = null;
    private DAOAnexo daoAnexo = null;
    private DAOCategoria daoCategoria = null;
    private DAOImagem daoImagem = null;


    public SalvarFeedTask(SalvarFeedService service, Intent intent, Feed feed, long idFeed){
        super();
        this.service=service;
        this.intent=intent;
        this.feed=feed;
        this.idFeed=idFeed;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        estimativa = estimativaDosFor()*2;
        mNotifyManager = (NotificationManager) C.getContext().getSystemService(Context.NOTIFICATION_SERVICE);
            mBuilder = new NotificationCompat.Builder(C.getContext())
                    .setContentTitle("Adicionando "+feed.getTitulo())
                    .setContentText("Adicionando novos registros.")
                    .setOngoing(true)
                    .setSmallIcon(R.drawable.ic_stat_white_nox)
                    .setProgress(estimativa,0,false);
                    //.build();
            mNotifyManager.notify(C.NOTIFICATION_ID_ADICIONAR_FEED, mBuilder.build());
    }

    @Override
    protected Void doInBackground(String... params) {
        addFeed();
        mBuilder.setContentText("Novo Feed adicionado.")
                .setProgress(0,0,false);
        mNotifyManager.notify(C.NOTIFICATION_ID_ADICIONAR_FEED, mBuilder.build());

        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        mBuilder.setProgress(estimativa,values[0],false);
        mNotifyManager.notify(C.NOTIFICATION_ID_ADICIONAR_FEED, mBuilder.build());
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        mBuilder.setProgress(0, 0, false)
                .setOngoing(false);
        mNotifyManager.notify(C.NOTIFICATION_ID_ADICIONAR_FEED, mBuilder.build());
        Toast.makeText(C.getContext(), feed.getTitulo()+" foi adicionado com sucesso.", Toast.LENGTH_SHORT).show();
        if (service!=null) {
            Log.i("MY-SERVICES", "SalvarFeedsTask - TRY STOP");
            this.cancel(false);
            service.stopService(intent);
        }

    }

    private int estimativaDosFor(){
        int i = 0;//pq pode adicionar apenas o feed sem nada (!)
        if (feed.getCategorias()!=null) {
            i += feed.getCategorias().size();
        }
        if (feed.getPosts()!=null) {
            i += feed.getPosts().size();
            for (Post post : feed.getPosts()) {
                if (post.getAnexos()!=null) {
                    i += post.getAnexos().size();
                }
                if (post.getCategorias()!=null) {
                    i += post.getCategorias().size();
                }
                if (post.getConteudos()!=null) {
                    i += post.getConteudos().size();
                }
            }
        }
        return i;
    }

    private void addFeed(){
        daoFeed = new DAOFeed(C.getContext());
        if (idFeed!=-1) {
            daoPost = new DAOPost(C.getContext());
            daoDescricao = new DAODescricao(C.getContext());
            daoConteudo = new DAOConteudo(C.getContext());
            daoAnexo = new DAOAnexo(C.getContext());
            daoCategoria = new DAOCategoria(C.getContext());
            daoImagem = new DAOImagem(C.getContext());

            if (feed.getCategorias()!=null) {
                //pega a categoria do feed
                for (int i = 0; i < feed.getCategorias().size(); i++) {
                    //objeto de Feed ainda não tem id. Cria um objeto apenas com o id retornado do insert
                    daoCategoria.inserir(feed.getCategorias().get(i), (new Feed.Builder().idFeed(idFeed).build()));
                    atual ++;
                    publishProgress(atual);
                }
            }
            if (feed.getImagem()!=null) {
                //pega a imagem do feed
                daoImagem.inserir(feed.getImagem(), idFeed);
            }
            if (feed.getPosts()!=null) {
                idsPost = new ArrayList<Long>();
                for (int i = 0; i < feed.getPosts().size(); i++) {
                    atual ++;
                    publishProgress(atual);

                    long idPost = daoPost.inserir(feed.getPosts().get(i), idFeed);
                    idsPost.add(idPost);
                    //pega as categorias dos posts
                    if (feed.getPosts().get(i).getCategorias()!=null) {
                        //pega a categoria do feed
                        for (int j = 0; j < feed.getPosts().get(i).getCategorias().size(); j++) {
                            //objeto de Post ainda não tem id. Cria um objeto apenas com o id retornado do insert
                            daoCategoria.inserir(feed.getPosts().get(i).getCategorias().get(j), (new Post.Builder().idPost(idPost).build()));
                            atual ++;
                            publishProgress(atual);
                        }

                    }
                    //pega os anexos do post
                    if (feed.getPosts().get(i).getAnexos()!=null) {
                        for (int j = 0; j < feed.getPosts().get(i).getAnexos().size(); j++) {
                            daoAnexo.inserir(feed.getPosts().get(i).getAnexos().get(j), idPost);
                            atual ++;
                            publishProgress(atual);
                        }
                    }
                    //para cada post tem uma ou mais descrições
                    if (feed.getPosts().get(i).getDescricao()!=null) {
                        daoDescricao.inserir(feed.getPosts().get(i).getDescricao(), idPost);
                    }
                    //para cada post tem uma ou mais conteudos
                    if (feed.getPosts().get(i).getConteudos()!=null) {
                        for (int j = 0; j < feed.getPosts().get(i).getConteudos().size(); j++) {
                            daoConteudo.inserir(feed.getPosts().get(i).getConteudos().get(j), idPost);
                            atual ++;
                            publishProgress(atual);
                        }
                    }

                }
            }
            atualiza();//atualiza flag de acesso
            DatabaseManager.getInstance().closeDatabase();
            DatabaseManager.getInstance().closeDatabase();
            DatabaseManager.getInstance().closeDatabase();
            DatabaseManager.getInstance().closeDatabase();
            DatabaseManager.getInstance().closeDatabase();
            DatabaseManager.getInstance().closeDatabase();
            DatabaseManager.getInstance().closeDatabase();
        }
        //TODO: COLOCAR UMA MUDANÇA DE FLAG NO FEED PARA SER ACESSÍVEL.
        //daoFeed.DatabaseManager.getInstance().closeDatabase();
        //TODO: JOGAR O PROCESSO DE ADIÇÃO EM BACKGROUND NUMA NOTIFICAÇÃO.
    }
    private void atualiza(){
        Feed idf= new Feed.Builder().idFeed(idFeed).build();
        daoFeed.atualizaAcesso(idf, 1);

        atual+=	daoCategoria.atualizaAcesso(idf, 1);
        publishProgress(atual);

        daoImagem.atualizaAcesso(idf, 1);
        for (Long idPost : idsPost) {
            Post post = new Post.Builder().idPost(idPost).build();

            atual+=	daoPost.atualizaAcesso(post, 1);
            publishProgress(atual);

            atual+=	daoCategoria.atualizaAcesso(post, 1);
            publishProgress(atual);

            daoDescricao.atualizaAcesso(post, 1);

            atual+=	daoConteudo.atualizaAcesso(post, 1);
            publishProgress(atual);

            atual+=	daoAnexo.atualizaAcesso(post, 1);
            publishProgress(atual);
        }
    }
}
