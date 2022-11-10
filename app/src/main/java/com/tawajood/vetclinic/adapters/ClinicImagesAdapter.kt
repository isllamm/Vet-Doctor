package com.tawajood.vetclinic.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tawajood.vetclinic.databinding.ItemClinicImageBinding
import com.tawajood.vetclinic.databinding.ItemSpecializationBinding
import com.tawajood.vetclinic.pojo.ImageClinic
import com.tawajood.vetclinic.pojo.Specialization

class ClinicImagesAdapter :
    RecyclerView.Adapter<ClinicImagesAdapter.ClinicImagesViewHolder>() {

    var images = mutableListOf<ImageClinic>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class ClinicImagesViewHolder(val binding: ItemClinicImageBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ClinicImagesAdapter.ClinicImagesViewHolder {
        val binding =
            ItemClinicImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ClinicImagesAdapter.ClinicImagesViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ClinicImagesAdapter.ClinicImagesViewHolder,
        position: Int
    ) {

        Glide.with(holder.itemView.context).load(images[position].image).into(holder.binding.image)
    }

    override fun getItemCount(): Int {
        return images.size
    }

}