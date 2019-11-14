package com.vp.favorites

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.vp.favorites_db.entities.MovieFavoriteEntity
import kotlinx.android.synthetic.main.activity_favorite.*

class FavoriteActivity : AppCompatActivity() {

    private val favoriteAdapter:FavoriteAdapter by lazy {
        FavoriteAdapter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)
        rcvListFavorites.adapter = favoriteAdapter
        rcvListFavorites.layoutManager = LinearLayoutManager(this)
        rcvListFavorites.setHasFixedSize(true)
        //favoriteAdapter.setItems(pelis)
    }




}
