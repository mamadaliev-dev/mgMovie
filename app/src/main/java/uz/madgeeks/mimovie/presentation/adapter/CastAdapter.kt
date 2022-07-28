package uz.madgeeks.mimovie.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.madgeeks.mimovie.BuildConfig
import uz.madgeeks.mimovie.data.movie_detail.model.remote.response.Cast
import uz.madgeeks.mimovie.databinding.ItemActorBinding

class CastAdapter : RecyclerView.Adapter<CastAdapter.MovieCardViewHolder>() {

    private var itemClickListener: ((id: Long) -> Unit)? = null

    fun setItemClickListener(f: (id: Long) -> Unit) {
        itemClickListener = f
    }

    var data = mutableListOf<Cast>()

    @SuppressLint("NotifyDataSetChanged")
    fun setPersons(nData: List<Cast>) {
        this.data.clear()
        this.data.addAll(nData)
        notifyDataSetChanged()
    }

    inner class MovieCardViewHolder(private val binding: ItemActorBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(data: Cast) {
            binding.apply {
                name.text = data.original_name
                character.text = data.character
                Glide.with(binding.root.context)
                    .load("${BuildConfig.BASE_IMAGE_URL}${data.profilePath}")
                    .into(binding.image)

                itemView.setOnClickListener {
                    itemClickListener?.invoke(data.id.toLong())
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MovieCardViewHolder(
        ItemActorBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: MovieCardViewHolder, position: Int) =
        holder.bindData(data[position])
}