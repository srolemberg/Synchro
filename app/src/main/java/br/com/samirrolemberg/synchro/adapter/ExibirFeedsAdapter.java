package br.com.samirrolemberg.synchro.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.com.samirrolemberg.synchro.R;
import br.com.samirrolemberg.synchro.model.Feed;

/**
 * Created by Samir on 31/01/2015.
 */
public class ExibirFeedsAdapter extends RecyclerView.Adapter<ExibirFeedsAdapter.ExibirFeedsViewHolder> {

    private List<Feed> feeds;

    public ExibirFeedsAdapter(List<Feed> feeds) {
        this.feeds = feeds;
    }

    @Override
    public int getItemCount() {
        return feeds.size();
    }

    @Override
    public void onBindViewHolder(ExibirFeedsViewHolder holder, int i) {
        Feed feed = feeds.get(i);
        holder.titulo.setText(feed.getTitulo());
        holder.link.setText(feed.getLink());
    }

    @Override
    public ExibirFeedsViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.item_adapter_exibir_feeds, viewGroup, false);

        return new ExibirFeedsViewHolder(itemView);
    }

    public static class ExibirFeedsViewHolder extends RecyclerView.ViewHolder {
        protected TextView titulo;
        protected TextView link;

        public ExibirFeedsViewHolder(View v) {
            super(v);
            titulo =  (TextView) v.findViewById(R.id.titulo_exibir_feeds);
            link = (TextView)  v.findViewById(R.id.link_exibir_feeds);
        }
    }
}
