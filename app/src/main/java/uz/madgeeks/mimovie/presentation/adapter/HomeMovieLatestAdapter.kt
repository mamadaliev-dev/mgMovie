package uz.madgeeks.mimovie.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.madgeeks.mimovie.BuildConfig
import uz.madgeeks.mimovie.data.home.model.local.MovieResultDto
import uz.madgeeks.mimovie.databinding.ItemMovieLatestBinding

class HomeMovieLatestAdapter : RecyclerView.Adapter<HomeMovieLatestAdapter.HomeMovieViewHolder>() {
    var _data = mutableListOf<MovieResultDto>()

    private var itemClickListener: ((id: Long) -> Unit)? = null

    fun setItemClickListener(f: (id: Long) -> Unit) {
        itemClickListener = f
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setMovies(newData: List<MovieResultDto>) {
        _data.clear()
        _data.addAll(newData)
        notifyDataSetChanged()
    }

    inner class HomeMovieViewHolder(private val binding: ItemMovieLatestBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bindView(data: MovieResultDto) {
            binding.apply {
                name.text = data.originalTitle
                Glide.with(binding.root.context)
                    .load("${BuildConfig.BASE_IMAGE_URL}${data.posterPath}")
                    .into(binding.image)

                itemView.setOnClickListener {
                    itemClickListener?.invoke(data.id)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        HomeMovieViewHolder(
            ItemMovieLatestBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun getItemCount() = _data.size

    override fun onBindViewHolder(holder: HomeMovieViewHolder, position: Int) =
        holder.bindView(_data[position])
}