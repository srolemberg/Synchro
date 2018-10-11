package br.com.srolemberg.synchro.feat.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.srolemberg.synchro.R

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_home)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, HomeFragment.newInstance())
                    .commitNow()
        }
    }

}
