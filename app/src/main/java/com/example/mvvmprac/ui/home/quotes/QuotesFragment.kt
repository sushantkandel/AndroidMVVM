package com.example.mvvmprac.ui.home.quotes

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvvmprac.R
import com.example.mvvmprac.data.db.entities.Quote
import com.example.mvvmprac.util.Coroutines
import com.example.mvvmprac.util.toast
import kotlinx.android.synthetic.main.quotes_fragment.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class QuotesFragment : Fragment(), KodeinAware {

    override val kodein by kodein()
    private val factory: QuotesViewModelFactory by instance()
    private lateinit var viewModel: QuotesViewModel
    private val adapter = QuotesRecyclerAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.quotes_fragment, container, false)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, factory).get(QuotesViewModel::class.java)
        Coroutines.main {
            val quotes = viewModel.quotes.await()
            quotes.observe(this, Observer {
                initializeUI(it)
            })
        }

    }

    private fun initializeUI(it: List<Quote>?) {

        val layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        quotesRecyclerView
            .layoutManager = layoutManager
        quotesRecyclerView.adapter = adapter
        adapter.submitData(it as ArrayList<Quote>)

    }

}
