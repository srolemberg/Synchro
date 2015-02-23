package br.com.samirrolemberg.synchro.activity;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;

import java.util.List;

import br.com.samirrolemberg.synchro.R;
import br.com.samirrolemberg.synchro.fragment.ExibirPostsFragment;
import br.com.samirrolemberg.synchro.model.Feed;
import br.com.samirrolemberg.synchro.util.C;

public class ExibirPostsActivity extends ActionBarActivity {

    private Feed feedAux;
    private List<Feed> feedsAux;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exibir_posts);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new ExibirPostsFragment())
                    .commit();
        }
        if (getIntent().getExtras()!=null){
            feedAux = (Feed) getIntent().getExtras().get(C.getContext().getString(R.string.m_Feed));
            getSupportActionBar().setTitle(feedAux.getTitulo());
        }

    }

    @Override
    public void onBackPressed() {
        NavUtils.navigateUpFromSameTask(this);
    }

}
