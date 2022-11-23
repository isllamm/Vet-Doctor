package com.tawajood.vetclinic.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tawajood.vetclinic.databinding.ItemClinicTimesEditBinding
import com.tawajood.vetclinic.databinding.ItemSpecializationEditBinding
import com.tawajood.vetclinic.pojo.ClinicDay
import com.tawajood.vetclinic.pojo.ShowTimes
import com.tawajood.vetclinic.pojo.Specialization

class EditTimeAdapter(private val onDeleteClickListener: OnDeleteClickListener) :
    RecyclerView.Adapter<EditTimeAdapter.EditTimeViewHolder>() {

    var clinicDays = mutableListOf<ClinicDay>()
    var times = mutableListOf<ShowTimes>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    fun setTime(times: MutableList<ShowTimes>) {
        this.times = times
        notifyDataSetChanged()
    }

    class EditTimeViewHolder(val binding: ItemClinicTimesEditBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EditTimeAdapter.EditTimeViewHolder {
        val binding =
            ItemClinicTimesEditBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return EditTimeAdapter.EditTimeViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: EditTimeAdapter.EditTimeViewHolder,
        position: Int
    ) {
        holder.binding.day.text = times[position].name
        holder.binding.from.text = times[position].from
        holder.binding.to.text = times[position].to

        holder.binding.ivDelete.setOnClickListener {
            onDeleteClickListener.onDeleteClickListener(position)
        }
    }

    override fun getItemCount(): Int {
        return times.size
    }

    interface OnDeleteClickListener {
        fun onDeleteClickListener(position: Int)
    }
}