package com.vp.favorites

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.vp.favorites_db.entities.MovieFavoriteEntity


class FavoriteAdapter(private val context: Context) : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    private var listItems: List<MovieFavoriteEntity> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        return FavoriteViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.favorites_item, parent, false))
    }

    override fun getItemCount(): Int {
        return listItems.size
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val listItem = listItems[position]
        holder.title.text = listItem.title
        holder.year.text = listItem.year
        holder.director.text = listItem.director
        holder.runtime.text = listItem.runtime


        val imageDensity = holder.image.resources.displayMetrics.density.toInt()
        Glide.with(context)
                .load(listItem.poster)
                .apply(RequestOptions().override(300 * imageDensity, 600 * imageDensity))
                .into(holder.image)

    }

    fun setItems(listItems: List<MovieFavoriteEntity>) {
        this.listItems = listItems
        notifyDataSetChanged()
    }

    inner class FavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val image: ImageView by lazy { itemView.findViewById<ImageView>(R.id.poster) }
        val title:TextView by lazy { itemView.findViewById<TextView>(R.id.title) }
        val year:TextView by lazy { itemView.findViewById<TextView>(R.id.year) }
        val director:TextView by lazy { itemView.findViewById<TextView>(R.id.director) }
        val runtime:TextView by lazy { itemView.findViewById<TextView>(R.id.runtime) }

    }

    companion object {
        private const val NO_IMAGE = "N/A"
    }


}