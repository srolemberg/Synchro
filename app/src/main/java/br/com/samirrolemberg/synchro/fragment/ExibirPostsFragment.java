package br.com.samirrolemberg.synchro.fragment;

import android.content.res.Configuration;
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
import br.com.samirrolemberg.synchro.adapter.ExibirPostsAdapter;
import br.com.samirrolemberg.synchro.conn.DatabaseManager;
import br.com.samirrolemberg.synchro.dao.DAOPost;
import br.com.samirrolemberg.synchro.model.Feed;
import br.com.samirrolemberg.synchro.model.Post;
import br.com.samirrolemberg.synchro.util.C;

public class ExibirPostsFragment extends Fragment {

    private ExibirPostsAdapter adapter1;
    private RelativeLayout rootView;
    private RecyclerView recyclerView;
    private StaggeredGridLayoutManager manager;

    int vertical = Configuration.ORIENTATION_PORTRAIT;
    int orientacao = -1;
    private int position = -1;

    private Feed feedAux;
    private List<Post> posts;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        Log.i("outState", "create");
        if (getActivity().getIntent().getExtras()!=null){
            feedAux = (Feed) getActivity().getIntent().getExtras().get(C.getContext().getString(R.string.m_Feed));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        orientacao = getResources().getConfiguration().orientation;
        rootView = (RelativeLayout) inflater.inflate(R.layout.fragment_exibir_posts, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.listaExibirPost);
        manager = new StaggeredGridLayoutManager(orientacao==vertical?1:2,StaggeredGridLayoutManager.VERTICAL);
        manager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        recyclerView.setLayoutManager(manager);

        DAOPost daoPost = new DAOPost(C.getContext());
        posts = daoPost.listarTudo(feedAux);
        DatabaseManager.getInstance().closeDatabase();

        if (posts.size()>0){
            adapter1 = new ExibirPostsAdapter(posts, ExibirPostsFragment.this);
            recyclerView.setAdapter(adapter1);
        }

        setRetainInstance(true);
        return rootView;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        CardView cardView = (CardView) v.findViewById(R.id.card_exibir_posts);
        position = (Integer) cardView.getTag();
        getActivity().getMenuInflater().inflate(R.menu.menu_fragment_lista_post, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id==R.id.menu_fragment_lista_post_detalhes){
            Toast.makeText(C.getContext(), "Detalhes: " + posts.get(position).getTitulo(), Toast.LENGTH_LONG).show();
        }
        return super.onContextItemSelected(item);
    }

}
