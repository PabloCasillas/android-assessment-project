package com.vp.favorites

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.vp.favorites.viewmodel.FavoritesViewModel
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_favorite.rcvListFavorites
import javax.inject.Inject

class FavoritesActivity : AppCompatActivity(), HasSupportFragmentInjector {
    @Inject
    protected lateinit var dispatchingActivityInjector: DispatchingAndroidInjector<Fragment>
    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private val favoritesAdapter: FavoritesAdapter by lazy {
        FavoritesAdapter(this)
    }
    private lateinit var favoritesViewModel: FavoritesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)
        rcvListFavorites.apply {
            adapter = favoritesAdapter
            layoutManager = LinearLayoutManager(this@FavoritesActivity)
            setHasFixedSize(true)
        }

        favoritesViewModel =
            ViewModelProviders.of(this, factory).get(FavoritesViewModel::class.java)
        favoritesViewModel.moviesFavorites.observe(this, Observer {
            favoritesAdapter.setItems(it)
        })

        favoritesViewModel.getFavoritesMovies()
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = dispatchingActivityInjector
}
