package br.com.samirrolemberg.synchro.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.List;

import br.com.samirrolemberg.synchro.R;
import br.com.samirrolemberg.synchro.adapter.ExibirFeedsAdapter;
import br.com.samirrolemberg.synchro.adapter.ExibirFeedsVazioAdapter;
import br.com.samirrolemberg.synchro.conn.DatabaseManager;
import br.com.samirrolemberg.synchro.dao.DAOFeed;
import br.com.samirrolemberg.synchro.model.Feed;
import br.com.samirrolemberg.synchro.service.LimparConteudoFeedService;
import br.com.samirrolemberg.synchro.util.C;
import br.com.samirrolemberg.synchro.util.U;

/**
 * Created by samir on 06/02/2015.
 */
public class InicialFragment extends Fragment {

    private List<Feed> feeds;
    private ExibirFeedsAdapter adapter1;
    private ExibirFeedsVazioAdapter adapter2;
    private DAOFeed daoFeed;

    private RelativeLayout rootView;
    private RecyclerView recyclerView;
    private StaggeredGridLayoutManager manager;

    int vertical = Configuration.ORIENTATION_PORTRAIT;
    int orientacao = -1;
    private int position = -1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        orientacao = getResources().getConfiguration().orientation;
        rootView = (RelativeLayout) inflater.inflate(R.layout.fragment_inicial, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.listaExibirFeeds);
        manager = new StaggeredGridLayoutManager(orientacao==vertical?1:2,StaggeredGridLayoutManager.VERTICAL);
        manager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        recyclerView.setLayoutManager(manager);

        daoFeed = new DAOFeed(C.getContext());
        feeds = daoFeed.listarImagem();
        DatabaseManager.getInstance().closeDatabase();

        if (feeds.size()>0){
            adapter1 = new ExibirFeedsAdapter(feeds, InicialFragment.this);
            recyclerView.setAdapter(adapter1);
        }else{
            feeds.add(new Feed.Builder().build());
            adapter2 = new ExibirFeedsVazioAdapter(feeds, InicialFragment.this);
            recyclerView.setAdapter(adapter2);
        }

        setRetainInstance(true);
        return rootView;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        CardView cardView = (CardView) v.findViewById(R.id.card_exibir_feeds);
        position = (Integer) cardView.getTag();
        getActivity().getMenuInflater().inflate(R.menu.menu_fragment_lista_feed, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id==R.id.menu_fragment_lista_feed_detalhes){
            Toast.makeText(C.getContext(), "Detalhes: "+feeds.get(position).getTitulo(), Toast.LENGTH_LONG).show();
        }else if(id==R.id.menu_fragment_lista_feed_abrir_no_navegador){
            if (U.isConnected(C.getContext())) {
                Uri uri = Uri.parse(feeds.get(position).getLink());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }else{
                Toast.makeText(C.getContext(), "Não há conexão de internet.", Toast.LENGTH_SHORT).show();
            }
        }else if(id==R.id.menu_fragment_lista_feed_atualizar_feed){
            Toast.makeText(C.getContext(), "Atualizar: "+feeds.get(position).getTitulo(), Toast.LENGTH_LONG).show();
        }else if(id==R.id.menu_fragment_lista_feed_limpar_conteudo){
            String frase = "Você removerá todo o conteúdo adicionado para este Feed anteriormente. Deseja continuar?";
            AlertDialog dialog = new AlertDialog.Builder(getActivity())
                    .setTitle("Atenção")
                    .setMessage(frase)
                    .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (!U.isMyServiceRunning(LimparConteudoFeedService.class, C.getContext())){
                                final Feed feed = feeds.get(position);
                                final long idFeed = feed.getIdFeed();

                                IntentFilter filter = new IntentFilter(C.getContext().getString(R.string.r_LimparConteudoFeed));
                                C.getContext().registerReceiver(C.receiverLimparConteudoFeed, filter);

                                Intent intent = new Intent();
                                intent.setAction(C.getContext().getString(R.string.r_LimparConteudoFeed));
                                intent.putExtra(C.getContext().getString(R.string.m_Feed), feed);
                                intent.putExtra(C.getContext().getString(R.string.m_idFeed,idFeed), idFeed);
                                C.getContext().sendBroadcast(intent);
                                Log.w("RECEIVER","INICIAL CLICK");
                            }else{
                                Toast.makeText(C.getContext(), "A operação está ocupada no momento. Aguarde alguns instantes.", Toast.LENGTH_LONG).show();
                            }
                        }
                    })
                    .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .setOnCancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {
                            dialog.dismiss();
                        }
                    })
                    .setCancelable(true)
                    .show();
        }else if(id==R.id.menu_fragment_lista_feed_excluir){
            Toast.makeText(C.getContext(), "Excluir: "+feeds.get(position).getTitulo(), Toast.LENGTH_LONG).show();
        }
        return super.onContextItemSelected(item);
    }

}