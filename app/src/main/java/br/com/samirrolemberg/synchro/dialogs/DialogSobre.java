package br.com.samirrolemberg.synchro.dialogs;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.samirrolemberg.synchro.R;

/**
 * Created by Samir on 17/02/2015.
 */
public class DialogSobre {

    private View view;

    public DialogSobre(View inflated){
        super();
        this.view = inflated;
    }

    public View build(){
        TextView tituloDialogo = (TextView) ((view.findViewById(R.id.dialog_titulo_customizado)).findViewById(R.id.texto_titulo_dialogo_customizado));
        tituloDialogo.setText("Sobre");
        ImageView imagemDialogo = (ImageView) ((view.findViewById(R.id.dialog_titulo_customizado)).findViewById(R.id.imagem_titulo_dialogo_customizado));
        imagemDialogo.setVisibility(View.GONE);

        //TextView titulo = (TextView) view.findViewById(R.id.titulo_dialog_sobre);
        //TextView autor  = (TextView) view.findViewById(R.id.autor_dialog_sobre);
        //ImageView imagem = (ImageView) view.findViewById(R.id.imagem_dialogo_sobre);

        return view;
    }
}
