package br.com.samirrolemberg.synchro.adapter;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import br.com.samirrolemberg.synchro.R;
import br.com.samirrolemberg.synchro.activity.ExibirPostsActivity;
import br.com.samirrolemberg.synchro.fragment.InicialFragment;
import br.com.samirrolemberg.synchro.model.Feed;
import br.com.samirrolemberg.synchro.util.C;

/**
 * Created by Samir on 31/01/2015.
 */
public class ExibirFeedsAdapter extends RecyclerView.Adapter<ExibirFeedsAdapter.ExibirFeedsViewHolder> {

    private List<Feed> feeds;
    private InicialFragment fragment;
    public ExibirFeedsAdapter(List<Feed> feeds, InicialFragment fragment) {
        this.feeds = feeds;
        this.fragment = fragment;
    }

    @Override
    public int getItemCount() {
        return feeds.size();
    }

    @Override
    public void onBindViewHolder(ExibirFeedsViewHolder holder, int i) {
        final Feed feed = feeds.get(i);
        holder.titulo.setText(feed.getTitulo());
        holder.link.setText(feed.getLink());
        if (feed.getImagem()!=null){
            if (!feed.getImagem().getUrl().isEmpty()){
                Picasso.with(C.getContext())
                        .load(feed.getImagem().getUrl())
                        .placeholder(C.getContext().getResources().getDrawable(R.drawable.ic_launcher))
                        .error(C.getContext().getResources().getDrawable(android.R.drawable.stat_notify_error))
                        .into(holder.imagem);
            }else{
                holder.imagem.setVisibility(View.GONE);
            }
        }else{
            holder.imagem.setVisibility(View.GONE);
        }
        holder.card.setTag(i);
        fragment.registerForContextMenu(holder.card);
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(fragment.getActivity(), ExibirPostsActivity.class);
                intent.putExtra(C.getContext().getString(R.string.m_Feed),feed);
                fragment.getActivity().startActivity(intent);
            }
        });
    }

    @Override
    public ExibirFeedsViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(C.getContext()).
                inflate(R.layout.item_adapter_exibir_feeds, viewGroup, false);

        return new ExibirFeedsViewHolder(itemView);
    }

    public static class ExibirFeedsViewHolder extends RecyclerView.ViewHolder {
        protected TextView titulo;
        protected TextView link;
        protected ImageView imagem;
        protected CardView card;

        public ExibirFeedsViewHolder(View v) {
            super(v);
            titulo  =   (TextView)  v.findViewById(R.id.titulo_exibir_feeds);
            link    =   (TextView)  v.findViewById(R.id.link_exibir_feeds);
            imagem  =   (ImageView) v.findViewById(R.id.imagem_exibir_feeds);
            card    =   (CardView)  v.findViewById(R.id.card_exibir_feeds);
        }
    }
}
