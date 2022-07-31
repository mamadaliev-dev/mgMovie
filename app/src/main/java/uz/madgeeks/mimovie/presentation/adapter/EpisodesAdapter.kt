package uz.madgeeks.mimovie.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.madgeeks.mimovie.BuildConfig
import uz.madgeeks.mimovie.R
import uz.madgeeks.mimovie.data.season_details.models.Episode
import uz.madgeeks.mimovie.databinding.ItemEpisodeBinding

class EpisodesAdapter : RecyclerView.Adapter<EpisodesAdapter.HomeMovieViewHolder>() {
    var _data = mutableListOf<Episode>()

    private var itemClickListener: ((season_number: Int, episode_number: Int) -> Unit)? = null

    fun setItemClickListener(f: (season_number: Int, episode_number: Int) -> Unit) {
        itemClickListener = f
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setEpisodes(newData: List<Episode>) {
        _data.clear()
        _data.addAll(newData)
        notifyDataSetChanged()
    }

    inner class HomeMovieViewHolder(private val binding: ItemEpisodeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bindView(data: Episode) {
            binding.apply {
                name.text = data.name
                overview.text = data.overview
                Glide.with(binding.root.context)
                    .load("${BuildConfig.BASE_IMAGE_URL}${data.still_path}")
                    .placeholder(R.drawable.ic_trailer)
                    .into(binding.image)

                itemView.setOnClickListener {
                    itemClickListener?.invoke(data.season_number, data.episode_number)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        HomeMovieViewHolder(
            ItemEpisodeBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun getItemCount() = _data.size

    override fun onBindViewHolder(holder: HomeMovieViewHolder, position: Int) =
        holder.bindView(_data[position])
}