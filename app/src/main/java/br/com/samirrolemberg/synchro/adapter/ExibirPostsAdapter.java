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

import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.Iterator;
import java.util.List;

import br.com.samirrolemberg.synchro.R;
import br.com.samirrolemberg.synchro.activity.LerPostActivity;
import br.com.samirrolemberg.synchro.conn.DatabaseManager;
import br.com.samirrolemberg.synchro.dao.DAOAnexo;
import br.com.samirrolemberg.synchro.dao.DAOConteudo;
import br.com.samirrolemberg.synchro.fragment.ExibirPostsFragment;
import br.com.samirrolemberg.synchro.model.Anexo;
import br.com.samirrolemberg.synchro.model.Conteudo;
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
        if (posts!=null||posts.size()>0){
            final DAOConteudo daoConteudo = new DAOConteudo(C.getContext());
            final DAOAnexo daoAnexo = new DAOAnexo(C.getContext());
            for (Post post: posts){
                    List<Anexo> anexos = daoAnexo.listarConteudo(post,"image");
                    if (anexos.size()==0) {//se post não tem anexo de imagem.
                        if (post != null) {
                            Conteudo conteudo = daoConteudo.buscar(post);
                            if (conteudo != null) {
                                Document document = Jsoup.parse(conteudo.getValor());
                                Elements elements = document.getElementsByTag("img");
                                if (elements != null || elements.size() > 0) {
                                    for (int e = 0; e < elements.size(); e++) {
                                        Attributes attributes = elements.get(e).attributes();
                                        Iterator<Attribute> iterator = attributes.iterator();
                                        while (iterator.hasNext()) {
                                            Attribute attribute = iterator.next();
                                            if (attribute.getKey().equals("src")) {
                                                String url = attribute.getValue();
                                                Anexo anexo = new Anexo.Builder()
                                                        .tamanho(0)
                                                        .tipo("image/" + url.substring(url.length() - 3, url.length()))
                                                        .url(url)
                                                        .acesso(0)
                                                        .build();
                                                daoAnexo.inserir(anexo, post.getIdPost());
                                            }//src
                                        }//while
                                    }//for element
                                }//if element
                            }//conteudo null
                        }//post null
                    }//else anexo
            }
            DatabaseManager.getInstance().closeDatabase();
            DatabaseManager.getInstance().closeDatabase();
        }//post not null
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

        DAOAnexo daoAnexo = new DAOAnexo(C.getContext());
        Anexo anexo = daoAnexo.buscarConteudo(post, "image");
        if (anexo!=null){
            if (!anexo.getUrl().trim().isEmpty()){
                Picasso.with(C.getContext()).load(anexo.getUrl()).into(holder.image);
                holder.image.setVisibility(View.VISIBLE);
            }
        }


        DatabaseManager.getInstance().closeDatabase();

        fragment.registerForContextMenu(holder.card);
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(fragment.getActivity(), LerPostActivity.class);
                intent.putExtra(C.getContext().getString(R.string.m_Post), post);
                fragment.getActivity().startActivity(intent);
            }
        });

//        final DAOConteudo daoConteudo = new DAOConteudo(C.getContext());
//        final DAOAnexo daoAnexo = new DAOAnexo(C.getContext());
//
//        List<Anexo> anexos = daoAnexo.listarConteudo(post,"image");
//
//        if (anexos.size()>0){//se tem anexo.
//            holder.image.setVisibility(View.GONE);
//            for (Anexo anexo : anexos){
//                if (anexo.getTipo().contains("image")){
//                    holder.image.setVisibility(View.VISIBLE);
//                    Picasso.with(C.getContext())
//                            .load(anexo.getUrl())
//                            .into(holder.image);
//                    break;
//                }
//            }
//        }else{
//            if (post != null) {
//                Conteudo conteudo = daoConteudo.buscar(post);
//                if (conteudo != null) {
//                    Document document = Jsoup.parse(conteudo.getValor());
//                    Elements elements = document.getElementsByTag("img");
//                    if (elements != null || elements.size() > 0) {
//                        for (int e = 0; e < elements.size(); e++) {
//                            Attributes attributes = elements.get(e).attributes();
//                            Iterator<Attribute> iterator = attributes.iterator();
//                            while (iterator.hasNext()) {
//                                Attribute attribute = iterator.next();
//                                if (attribute.getKey().equals("src")) {
//                                    String url = attribute.getValue();
//                                    Anexo anexo = new Anexo.Builder()
//                                            .tamanho(0)
//                                            .tipo("image/" + url.substring(url.length() - 3, url.length()))
//                                            .url(url)
//                                            .acesso(0)
//                                            .build();
//                                    daoAnexo.inserir(anexo, post.getIdPost());
//                                }//src
//                            }//while
//                        }//for element
//                    }//if element
//                }//conteudo null
//            }//post null
//            List<Anexo> anexos2 = daoAnexo.listarConteudo(post,"image");
//
//            if (anexos.size()>0) {//se tem anexo.
//                holder.image.setVisibility(View.GONE);
//                for (Anexo anexo : anexos) {
//                    if (anexo.getTipo().contains("image")){
//                        holder.image.setVisibility(View.VISIBLE);
//                        Picasso.with(C.getContext())
//                                .load(anexo.getUrl())
//                                .into(holder.image);
//                        break;
//                    }
//                }
//            }else{
//                holder.image.setVisibility(View.GONE);
//            }
//        }//else anexo
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
