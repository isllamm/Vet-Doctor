package com.tawajood.vetclinic.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tawajood.vetclinic.databinding.ItemClinicImageBinding
import com.tawajood.vetclinic.databinding.ItemClinicTimesBinding
import com.tawajood.vetclinic.pojo.ClinicDay
import com.tawajood.vetclinic.pojo.ImageClinic

class ClinicTimesAdapter :
    RecyclerView.Adapter<ClinicTimesAdapter.ClinicTimesViewHolder>() {

    var times = mutableListOf<ClinicDay>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class ClinicTimesViewHolder(val binding: ItemClinicTimesBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ClinicTimesAdapter.ClinicTimesViewHolder {
        val binding =
            ItemClinicTimesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ClinicTimesAdapter.ClinicTimesViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ClinicTimesAdapter.ClinicTimesViewHolder,
        position: Int
    ) {
        holder.binding.day.text = times[position].day.name
        holder.binding.from.text = times[position].times[0].from
        holder.binding.to.text = times[position].times[0].to
    }

    override fun getItemCount(): Int {
        return times.size
    }

}