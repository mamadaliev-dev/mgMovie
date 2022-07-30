package uz.madgeeks.mimovie.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.madgeeks.mimovie.BuildConfig
import uz.madgeeks.mimovie.R
import uz.madgeeks.mimovie.data.actors.model.remote.movie_credits.ActorCastInMovies
import uz.madgeeks.mimovie.data.movie_detail.model.remote.response.Cast
import uz.madgeeks.mimovie.databinding.ItemActorBinding
import uz.madgeeks.mimovie.databinding.ItemActorCreditInMovieBinding

class ActorCreditInMovieAdapter : RecyclerView.Adapter<ActorCreditInMovieAdapter.MovieCardViewHolder>() {

    private var itemClickListener: ((id: Long) -> Unit)? = null

    fun setItemClickListener(f: (id: Long) -> Unit) {
        itemClickListener = f
    }

    var data = mutableListOf<ActorCastInMovies>()

    @SuppressLint("NotifyDataSetChanged")
    fun setPersons(nData: List<ActorCastInMovies>) {
        this.data.clear()
        this.data.addAll(nData)
        notifyDataSetChanged()
    }

    inner class MovieCardViewHolder(private val binding: ItemActorCreditInMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(data: ActorCastInMovies) {
            binding.apply {
                name.text = data.original_title
                character.text = data.character
                Glide.with(binding.root.context)
                    .load("${BuildConfig.BASE_IMAGE_URL}${data.poster_path}")
                    .placeholder(R.drawable.ic_trailer)
                    .into(binding.image)

                itemView.setOnClickListener {
                    itemClickListener?.invoke(data.id.toLong())
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MovieCardViewHolder(
        ItemActorCreditInMovieBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: MovieCardViewHolder, position: Int) =
        holder.bindData(data[position])
}