package com.tawajood.vetclinic.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tawajood.vetclinic.databinding.ItemMedicineBinding
import com.tawajood.vetclinic.databinding.ItemReviewBinding
import com.tawajood.vetclinic.pojo.PreviousRequests
import com.tawajood.vetclinic.pojo.ReviewData

class ReviewsAdapter :
    RecyclerView.Adapter<ReviewsAdapter.ReviewsViewHolder>() {

    var reviews = mutableListOf<ReviewData>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class ReviewsViewHolder(val binding: ItemReviewBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ReviewsAdapter.ReviewsViewHolder {
        val binding =
            ItemReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReviewsAdapter.ReviewsViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ReviewsAdapter.ReviewsViewHolder,
        position: Int
    ) {
        Glide.with(holder.itemView.context).load(reviews[position].user.image)
            .into(holder.binding.ivUser)
        holder.binding.tvRate.text = reviews[position].rate.toString()
        holder.binding.comment.text = reviews[position].comment
    }

    override fun getItemCount(): Int {
        return reviews.size
    }

}