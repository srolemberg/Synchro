package br.com.srolemberg.synchro.feat.addfeed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import br.com.srolemberg.synchro.R

class AddFeedFragment : Fragment() {

    companion object {
        fun newInstance() = AddFeedFragment()
    }

    private lateinit var viewModel: AddFeedViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fgm_add_feed, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AddFeedViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
