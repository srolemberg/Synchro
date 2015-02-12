package br.com.samirrolemberg.synchro.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import br.com.samirrolemberg.synchro.R;
import br.com.samirrolemberg.synchro.adapter.ExibirFeedsAdapter;
import br.com.samirrolemberg.synchro.model.Feed;

/**
 * Created by samir on 06/02/2015.
 */
public class InicialFragment extends Fragment {

    public InicialFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RelativeLayout rootView = (RelativeLayout) inflater.inflate(R.layout.fragment_inicial, container, false);
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.listaExibirFeeds);
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL);
        manager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        recyclerView.setLayoutManager(manager);


        List<Feed> feeds = new ArrayList<>();

        for (int i = 0; i<10; i++){
            Feed feed = new Feed.Builder().titulo("Titulo: "+i).link("Link"+i).build();
            feeds.add(feed);
        }

        recyclerView.setAdapter(new ExibirFeedsAdapter(feeds));

        setRetainInstance(true);
        return rootView;
    }
}