package com.tawajood.vetclinic.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tawajood.vetclinic.databinding.ItemClinicTimesEditBinding
import com.tawajood.vetclinic.databinding.ItemSpecializationEditBinding
import com.tawajood.vetclinic.pojo.ClinicDay
import com.tawajood.vetclinic.pojo.Specialization

class EditTimeAdapter(private val onDeleteClickListener: OnDeleteClickListener) :
    RecyclerView.Adapter<EditTimeAdapter.EditTimeViewHolder>() {

    var clinicDays = mutableListOf<ClinicDay>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    fun setTime(clinicDays: MutableList<ClinicDay>) {
        this.clinicDays = clinicDays
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
        holder.binding.day.text = clinicDays.elementAt(position).day.name
        holder.binding.from.text = clinicDays.elementAt(position).times[0].from
        holder.binding.to.text = clinicDays.elementAt(position).times[0].to

        holder.binding.ivDelete.setOnClickListener {
            onDeleteClickListener.onDeleteClickListener(position)
        }
    }

    override fun getItemCount(): Int {
        return clinicDays.size
    }

    interface OnDeleteClickListener {
        fun onDeleteClickListener(position: Int)
    }
}