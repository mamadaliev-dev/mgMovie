package uz.madgeeks.mimovie.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.madgeeks.mimovie.BuildConfig
import uz.madgeeks.mimovie.data.movie_detail.model.local.CompanyResultDto
import uz.madgeeks.mimovie.databinding.ItemCompanyBinding

class CompanyAdapter : RecyclerView.Adapter<CompanyAdapter.MovieCardViewHolder>() {

    private var itemClickListener: ((id: Int) -> Unit)? = null

    fun setItemClickListener(f: (id: Int) -> Unit) {
        itemClickListener = f
    }

    var data = mutableListOf<CompanyResultDto>()

    @SuppressLint("NotifyDataSetChanged")
    fun setCompanies(nData: List<CompanyResultDto>) {
        this.data.clear()
        this.data.addAll(nData)
        notifyDataSetChanged()
    }

    inner class MovieCardViewHolder(private val binding: ItemCompanyBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(data: CompanyResultDto) {
            binding.apply {
                name.text = data.name
                Glide.with(binding.root.context)
                    .load("${BuildConfig.BASE_IMAGE_URL}${data.logo_path}")
                    .into(binding.logo)

                itemView.setOnClickListener {
                    itemClickListener?.invoke(data.id)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MovieCardViewHolder(
        ItemCompanyBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: MovieCardViewHolder, position: Int) =
        holder.bindData(data[position])
}