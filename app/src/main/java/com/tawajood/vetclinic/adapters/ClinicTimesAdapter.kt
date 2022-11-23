package com.tawajood.vetclinic.adapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tawajood.vetclinic.databinding.ItemClinicImageBinding
import com.tawajood.vetclinic.databinding.ItemClinicTimesBinding
import com.tawajood.vetclinic.pojo.ClinicDay
import com.tawajood.vetclinic.pojo.ImageClinic
import com.tawajood.vetclinic.pojo.ShowTimes
import com.tawajood.vetclinic.pojo.Times

class ClinicTimesAdapter :
    RecyclerView.Adapter<ClinicTimesAdapter.ClinicTimesViewHolder>() {

    var times = mutableListOf<ShowTimes>()
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

        holder.binding.day.text = times[position].name
        holder.binding.from.text = times[position].from
        holder.binding.to.text = times[position].to

    }

    override fun getItemCount(): Int {
        return times.size
    }

}