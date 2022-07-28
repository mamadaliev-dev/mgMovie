package uz.madgeeks.mimovie.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.madgeeks.mimovie.BuildConfig
import uz.madgeeks.mimovie.data.movie_detail.model.remote.response.Review
import uz.madgeeks.mimovie.databinding.ItemReviewBinding

class ReviewAdapter : RecyclerView.Adapter<ReviewAdapter.MovieCardViewHolder>() {

    private var itemClickListener: ((id: Int) -> Unit)? = null

    fun setItemClickListener(f: (id: Int) -> Unit) {
        itemClickListener = f
    }

    var data = mutableListOf<Review>()

    @SuppressLint("NotifyDataSetChanged")
    fun setReviews(nData: List<Review>) {
        this.data.clear()
        this.data.addAll(nData)
        notifyDataSetChanged()
    }

    inner class MovieCardViewHolder(private val binding: ItemReviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(data: Review) {
            binding.apply {
                name.text = data.results.author
                review.text = data.results.content
                Glide.with(binding.root.context)
                    .load("${BuildConfig.BASE_IMAGE_URL}${data.results.authorDetails.avatar_path}")
                    .into(binding.image)

                itemView.setOnClickListener {
                    itemClickListener?.invoke(data.id)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MovieCardViewHolder(
        ItemReviewBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: MovieCardViewHolder, position: Int) =
        holder.bindData(data[position])
}