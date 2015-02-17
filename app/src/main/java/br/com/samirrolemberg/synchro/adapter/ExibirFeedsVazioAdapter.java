package br.com.samirrolemberg.synchro.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.com.samirrolemberg.synchro.R;
import br.com.samirrolemberg.synchro.fragment.InicialFragment;
import br.com.samirrolemberg.synchro.model.Feed;
import br.com.samirrolemberg.synchro.util.C;

/**
 * Created by Samir on 31/01/2015.
 */
public class ExibirFeedsVazioAdapter extends RecyclerView.Adapter<ExibirFeedsVazioAdapter.ExibirFeedsViewHolder> {

    private List<Feed> feeds;
    private InicialFragment fragment;
    public ExibirFeedsVazioAdapter(List<Feed> feeds, InicialFragment fragment) {
        this.feeds = feeds;
        this.fragment = fragment;
    }

    @Override
    public int getItemCount() {
        return feeds.size();
    }

    @Override
    public void onBindViewHolder(ExibirFeedsViewHolder holder, int i) {
//        final Feed feed = feeds.get(i);
//        holder.titulo.setText(feed.getTitulo());
//        holder.link.setText(feed.getLink());
//        if (feed.getImagem()!=null){
//            Picasso.with(C.getContext())
//                    .load(feed.getImagem().getUrl())
//                    .placeholder(C.getContext().getResources().getDrawable(R.drawable.ic_launcher))
//                    .error(C.getContext().getResources().getDrawable(android.R.drawable.stat_notify_error))
//                    .into(holder.imagem);
//        }
//        holder.card.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(C.getContext(), "Click: " + feed.getTitulo(), Toast.LENGTH_LONG).show();
//            }
//        });
//        holder.card.setTag(i);
//        fragment.registerForContextMenu(holder.card);
    }

    @Override
    public ExibirFeedsViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(C.getContext()).
                inflate(R.layout.item_adapter_exibir_feeds_vazio, viewGroup, false);

        return new ExibirFeedsViewHolder(itemView);
    }

    public static class ExibirFeedsViewHolder extends RecyclerView.ViewHolder {
        protected TextView texto;

        public ExibirFeedsViewHolder(View v) {
            super(v);
            texto  =   (TextView)  v.findViewById(R.id.texto_exibir_feeds_vazio);
        }
    }
}
