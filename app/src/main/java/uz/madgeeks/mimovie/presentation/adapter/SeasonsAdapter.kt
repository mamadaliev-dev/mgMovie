package uz.madgeeks.mimovie.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.madgeeks.mimovie.BuildConfig
import uz.madgeeks.mimovie.data.tv_show_details.models.Season
import uz.madgeeks.mimovie.data.tv_shows.models.TVShowsResult
import uz.madgeeks.mimovie.databinding.ItemTvshowsBinding

class SeasonsAdapter : RecyclerView.Adapter<SeasonsAdapter.HomeMovieViewHolder>() {
    var _data = mutableListOf<Season>()

    private var itemClickListener: ((id: Int) -> Unit)? = null

    fun setItemClickListener(f: (id: Int) -> Unit) {
        itemClickListener = f
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setSeasons(newData: List<Season>) {
        _data.clear()
        _data.addAll(newData)
        notifyDataSetChanged()
    }

    inner class HomeMovieViewHolder(private val binding: ItemTvshowsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bindView(data: Season) {
            binding.apply {
                name.text = data.name
                Glide.with(binding.root.context)
                    .load("${BuildConfig.BASE_IMAGE_URL}${data.poster_path}")
                    .into(binding.image)

                itemView.setOnClickListener {
                    itemClickListener?.invoke(data.season_number)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        HomeMovieViewHolder(
            ItemTvshowsBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun getItemCount() = _data.size

    override fun onBindViewHolder(holder: HomeMovieViewHolder, position: Int) =
        holder.bindView(_data[position])
}