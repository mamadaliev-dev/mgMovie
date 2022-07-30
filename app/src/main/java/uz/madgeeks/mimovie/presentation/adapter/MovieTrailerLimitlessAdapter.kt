package uz.madgeeks.mimovie.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.madgeeks.mimovie.data.movie_detail.model.remote.response.MovieTrailerResponse
import uz.madgeeks.mimovie.databinding.ItemMovieTrailerBinding
import uz.madgeeks.mimovie.databinding.ItemMovieTrailerFullBinding

class MovieTrailerLimitlessAdapter : RecyclerView.Adapter<MovieTrailerLimitlessAdapter.HomeMovieViewHolder>() {
    var _data = mutableListOf<MovieTrailerResponse>()

    private var itemClickListener: ((key: String) -> Unit)? = null

    fun setItemClickListener(f: (key: String) -> Unit) {
        itemClickListener = f
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setTrailers(newData: List<MovieTrailerResponse>) {
        _data.clear()
        _data.addAll(newData)
        notifyDataSetChanged()
    }

    inner class HomeMovieViewHolder(private val binding: ItemMovieTrailerFullBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bindView(data: MovieTrailerResponse) {
            binding.apply {
                name.text = data.name
                Glide.with(root.context)
                    .load("https://img.youtube.com/vi/${data.key}/mqdefault.jpg")
                    .into(image)

                itemView.setOnClickListener {
                    itemClickListener?.invoke(data.key)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        HomeMovieViewHolder(
            ItemMovieTrailerFullBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun getItemCount() = _data.size

    override fun onBindViewHolder(holder: HomeMovieViewHolder, position: Int) =
        holder.bindView(_data[position])
}