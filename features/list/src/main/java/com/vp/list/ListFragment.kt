package com.vp.list

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle

import com.vp.navigation.Navigator
import com.google.android.material.bottomnavigation.BottomNavigationView

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.ViewAnimator
import androidx.lifecycle.Observer

import com.vp.list.viewmodel.SearchResult
import com.vp.list.viewmodel.ListViewModel

import javax.inject.Inject

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.vp.list.viewmodel.ListState
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_list.*

class ListFragment : Fragment(), GridPagingScrollListener.LoadMoreItemsListener{

    @Inject
    internal lateinit var factory: ViewModelProvider.Factory
    @Inject
    internal lateinit var navigator: Navigator

    private var currentQuery:String = "Interview";

    private lateinit var listViewModel: ListViewModel
    private lateinit var gridPagingScrollListener: GridPagingScrollListener

    private val listAdapter: ListAdapter by lazy {
        ListAdapter(::onItemClick)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidSupportInjection.inject(this)
        listViewModel = ViewModelProviders.of(this, factory).get(ListViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        swipeRefreshLayout.setOnRefreshListener { listViewModel.searchMoviesByTitle(currentQuery, 1) }
        if (savedInstanceState != null) {
            currentQuery = savedInstanceState.getString(CURRENT_QUERY)
        }

        initBottomNavigation(view)
        initList()
        listViewModel.observeMovies().observe(this, Observer{ searchResult ->

                handleResult(listAdapter, searchResult)

        })
        listViewModel.searchMoviesByTitle(currentQuery, 1)
        showProgressBar()
    }

    private fun initBottomNavigation(view: View) {
        val bottomNavigationView = view.findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            if (item.itemId == R.id.favorites) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("app://movies/favorites"))
                intent.setPackage(requireContext().packageName)
                startActivity(intent)
            }
            true
        }
    }

    private fun initList() {

        recyclerView.adapter = listAdapter
        recyclerView.setHasFixedSize(true)
        val layoutManager = GridLayoutManager(context,
                if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT)
                    2
                else
                    3)
        recyclerView.layoutManager = layoutManager

        // Pagination
        gridPagingScrollListener = GridPagingScrollListener(layoutManager)
        gridPagingScrollListener.setLoadMoreItemsListener(this)
        recyclerView.addOnScrollListener(gridPagingScrollListener)
    }

    private fun showProgressBar() {
        viewAnimator.displayedChild = viewAnimator.indexOfChild(progressBar)
    }

    private fun showList() {
        viewAnimator.displayedChild = viewAnimator.indexOfChild(recyclerView)

    }

    private fun showError() {
        viewAnimator.displayedChild = viewAnimator.indexOfChild(errorText)
    }

    private fun handleResult(listAdapter: ListAdapter,
                             searchResult: SearchResult) {
        when (searchResult.listState) {
            ListState.LOADED -> {
                setItemsData(listAdapter, searchResult)
                showList()
                swipeRefreshLayout!!.isRefreshing = false
            }
            ListState.IN_PROGRESS -> {
                showProgressBar()
            }
            else -> {
                showError()
            }
        }
        gridPagingScrollListener.markLoading(false)
    }

    private fun setItemsData(listAdapter: ListAdapter,
                             searchResult: SearchResult) {
        listAdapter.setItems(searchResult.items.toMutableList())

        if (searchResult.totalResult <= listAdapter.itemCount) {
            gridPagingScrollListener.markLastPage(true)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(CURRENT_QUERY, currentQuery)
    }

    override fun loadMoreItems(page: Int) {
        gridPagingScrollListener.markLoading(true)
        listViewModel.searchMoviesByTitle(currentQuery, page)
    }

    fun submitSearchQuery(query: String) {
        currentQuery = query
        listAdapter.clearItems()
        listViewModel.searchMoviesByTitle(query, 1)
        showProgressBar()
    }

    private fun onItemClick(imdbID: String) {
        val intent = navigator.getDetailIntent(imdbID)
        startActivity(intent)
    }

    companion object {

        const val TAG = "ListFragment"
        private const val CURRENT_QUERY = "current_query"
    }
}