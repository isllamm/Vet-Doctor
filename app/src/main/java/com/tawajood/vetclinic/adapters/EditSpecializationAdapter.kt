package com.tawajood.vetclinic.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anilokcun.uwmediapicker.model.UwMediaPickerMediaModel
import com.tawajood.vetclinic.databinding.ItemSpecializationEditBinding
import com.tawajood.vetclinic.pojo.Specialization

class EditSpecializationAdapter(private val onDeleteClickListener: OnDeleteClickListener) :
    RecyclerView.Adapter<EditSpecializationAdapter.EditSpecializationViewHolder>() {

    var specializations = mutableSetOf<Specialization>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    fun getSet(): ArrayList<String> {
        val ids = ArrayList<String>()
        specializations.forEach { specialization ->
            ids.add(specialization.specialization_id.toString())
        }
        return ids
    }

    fun setSpecialization(specialization: MutableSet<Specialization>) {
        this.specializations = specialization
        notifyDataSetChanged()
    }

    class EditSpecializationViewHolder(val binding: ItemSpecializationEditBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EditSpecializationAdapter.EditSpecializationViewHolder {
        val binding =
            ItemSpecializationEditBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return EditSpecializationAdapter.EditSpecializationViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: EditSpecializationAdapter.EditSpecializationViewHolder,
        position: Int
    ) {
        holder.binding.name.text = specializations.elementAt(position).specialization.name

        holder.binding.ivDelete.setOnClickListener {
            onDeleteClickListener.onDeleteClickListener(position)
        }
    }

    override fun getItemCount(): Int {
        return specializations.size
    }

    interface OnDeleteClickListener {
        fun onDeleteClickListener(position: Int)
    }
}