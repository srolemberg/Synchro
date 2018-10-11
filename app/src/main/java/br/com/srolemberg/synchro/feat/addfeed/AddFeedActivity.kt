package br.com.srolemberg.synchro.feat.addfeed

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.srolemberg.synchro.R

class AddFeedActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_add_feed)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, AddFeedFragment.newInstance())
                    .commitNow()
        }
    }

}
