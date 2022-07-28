package uz.madgeeks.mimovie.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.madgeeks.mimovie.BuildConfig
import uz.madgeeks.mimovie.data.getYear
import uz.madgeeks.mimovie.data.tv_shows.models.TVShowsResult
import uz.madgeeks.mimovie.databinding.ItemMovieSearchLatestBinding

class TVShowSearchAdapter : RecyclerView.Adapter<TVShowSearchAdapter.HomeMovieViewHolder>() {
    var _data = mutableListOf<TVShowsResult>()

    private var itemClickListener: ((id: Long) -> Unit)? = null

    fun setItemClickListener(f: (id: Long) -> Unit) {
        itemClickListener = f
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setTVShows(newData: List<TVShowsResult>) {
        _data.clear()
        _data.addAll(newData)
        notifyDataSetChanged()
    }

    inner class HomeMovieViewHolder(private val binding: ItemMovieSearchLatestBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bindView(data: TVShowsResult) {
            binding.apply {
                name.text = data.original_name
                Glide.with(binding.root.context)
                    .load("${BuildConfig.BASE_IMAGE_URL}${data.poster_path}")
                    .into(binding.image)

                itemView.setOnClickListener {
                    itemClickListener?.invoke(data.id.toLong())
                }

                binding.additions.visibility = View.GONE
            }


//            if (data.first_air_date.isNotEmpty()) {
//                binding.additions.text =
//                    "${data.first_air_date.getYear()}  |  ${data.vote_average}★"
//            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        HomeMovieViewHolder(
            ItemMovieSearchLatestBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun getItemCount() = _data.size

    override fun onBindViewHolder(holder: HomeMovieViewHolder, position: Int) =
        holder.bindView(_data[position])
}