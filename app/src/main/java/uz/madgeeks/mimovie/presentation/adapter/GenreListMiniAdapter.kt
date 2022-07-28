package uz.madgeeks.mimovie.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.madgeeks.mimovie.data.genre.local.Genre
import uz.madgeeks.mimovie.databinding.ItemGenreBinding
import uz.madgeeks.mimovie.databinding.ItemGenreMiniBinding

class GenreListMiniAdapter : RecyclerView.Adapter<GenreListMiniAdapter.MovieCardViewHolder>() {

    private var itemClickListener: ((id: Int, name: String) -> Unit)? = null

    fun setItemClickListener(f: (id: Int, name: String) -> Unit) {
        itemClickListener = f
    }

    var data = mutableListOf<Genre>()

    @SuppressLint("NotifyDataSetChanged")
    fun setGenres(nData: List<Genre>) {
        this.data.clear()
        this.data.addAll(nData)
        notifyDataSetChanged()
    }

    inner class MovieCardViewHolder(private val binding: ItemGenreMiniBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(data: Genre) {
            binding.apply {
                name.text = data.name

                itemView.setOnClickListener {
                    itemClickListener?.invoke(data.id, data.name)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MovieCardViewHolder(
        ItemGenreMiniBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: MovieCardViewHolder, position: Int) =
        holder.bindData(data[position])
}