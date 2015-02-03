package br.com.samirrolemberg.synchro;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import br.com.samirrolemberg.synchro.adapter.ExibirFeedsAdapter;
import br.com.samirrolemberg.synchro.model.Feed;


public class MainActivity extends ActionBarActivity {

    private RecyclerView recList = null;
    private StaggeredGridLayoutManager layoutManager = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_lista_exibir_feeds);
        recList = (RecyclerView) findViewById(R.id.listaExibirFeeds);
        recList.setHasFixedSize(true);
        layoutManager = new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        recList.setLayoutManager(layoutManager);

        List<Feed> feeds = new ArrayList<>();

        for (int i = 0; i<10000; i++){
            Feed feed = new Feed.Builder().titulo("Titulo: "+i).link("Link"+i).build();
            feeds.add(feed);
        }

        recList.setAdapter(new ExibirFeedsAdapter(feeds));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
