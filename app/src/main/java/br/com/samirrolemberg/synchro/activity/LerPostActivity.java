package br.com.samirrolemberg.synchro.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.view.Menu;
import android.view.MenuItem;

import br.com.samirrolemberg.synchro.R;
import br.com.samirrolemberg.synchro.fragment.LerPostFragment;
import br.com.samirrolemberg.synchro.model.Post;
import br.com.samirrolemberg.synchro.util.C;

public class LerPostActivity extends AppCompatActivity {

    private Post postAux;
    //private List<Feed> feedsAux;
    private ShareActionProvider mShareActionProvider = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exibir_posts);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new LerPostFragment())
                    .commit();
        }
        if (getIntent().getExtras() != null) {
            postAux = (Post) getIntent().getExtras().get(C.getContext().getString(R.string.m_Post));
            getSupportActionBar().setTitle(postAux.getTitulo());
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        } else if (item.getItemId() == R.id.action_fragment_exibir_post_compartilhar) {
            startActivity(doShare());
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_fragment_ler_post, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private Intent doShare() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        //intent.putExtra(Intent.EXTRA_SUBJECT, postAux.getDescricao());
        intent.putExtra(Intent.EXTRA_TEXT, postAux.getLink());
        intent.putExtra(Intent.EXTRA_TITLE, postAux.getTitulo());
        return intent;
    }

}
