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
import br.com.samirrolemberg.synchro.activity.LerPostActivity;
import br.com.samirrolemberg.synchro.fragment.ExibirPostsFragment;
import br.com.samirrolemberg.synchro.model.Post;
import br.com.samirrolemberg.synchro.util.C;
import br.com.samirrolemberg.synchro.util.U;

/**
 * Created by Samir on 31/01/2015.
 */
public class ExibirPostsAdapter extends RecyclerView.Adapter<ExibirPostsAdapter.ExibirPostsViewHolder> {

    private List<Post> posts;
    private ExibirPostsFragment fragment;
    public ExibirPostsAdapter(List<Post> posts, ExibirPostsFragment fragment) {
        this.posts = posts;
        this.fragment = fragment;
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    @Override
    public void onBindViewHolder(final ExibirPostsViewHolder holder, int i) {
        final Post post = posts.get(i);
        holder.titulo.setText(post.getTitulo());
        holder.autor.setText(post.getAutor());
        holder.data.setText(U.time_24_date_mask(post.getData_publicacao(), C.getContext()));

        holder.card.setTag(i);

        fragment.registerForContextMenu(holder.card);
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(fragment.getActivity(), LerPostActivity.class);
                intent.putExtra(C.getContext().getString(R.string.m_Post), post);
                fragment.getActivity().startActivity(intent);
            }
        });

        Picasso.with(C.getContext()).load("http://barbarella.deadendthrills.com/imagestore/DET3/homeworldremasteredcollection/large/underway.png").into(holder.image);
    }

    @Override
    public ExibirPostsViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(C.getContext()).
                inflate(R.layout.item_adapter_exibir_posts, viewGroup, false);

        return new ExibirPostsViewHolder(itemView);
    }

    public static class ExibirPostsViewHolder extends RecyclerView.ViewHolder {
        protected TextView titulo;
        protected TextView autor;
        protected TextView data;
        protected CardView card;
        protected ImageView image;

        public ExibirPostsViewHolder(View v) {
            super(v);
            titulo  =   (TextView)  v.findViewById(R.id.titulo_exibir_posts);
            autor   =   (TextView)  v.findViewById(R.id.autor_exibir_posts);
            data    =   (TextView)  v.findViewById(R.id.data_exibir_posts);
            card    =   (CardView)  v.findViewById(R.id.card_exibir_posts);
            image   =   (ImageView) v.findViewById(R.id.image_card);
        }
    }
}
