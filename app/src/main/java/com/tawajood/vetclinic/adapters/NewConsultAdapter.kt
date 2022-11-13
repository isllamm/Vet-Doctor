package com.tawajood.vetclinic.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tawajood.vetclinic.databinding.ItemCurrentBinding
import com.tawajood.vetclinic.databinding.ItemNewBinding
import com.tawajood.vetclinic.databinding.ItemPreviousBinding
import com.tawajood.vetclinic.pojo.Consultant

class NewConsultAdapter(private val onItemClick: OnItemClick) :
    RecyclerView.Adapter<NewConsultAdapter.NewConsultViewHolder>() {

    var consultants = mutableListOf<Consultant>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class NewConsultViewHolder(val binding: ItemNewBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NewConsultAdapter.NewConsultViewHolder {
        val binding =
            ItemNewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewConsultAdapter.NewConsultViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: NewConsultAdapter.NewConsultViewHolder,
        position: Int
    ) {
        holder.binding.date.text = consultants[position].created_at.substring(0, 10)
        holder.binding.type.text = consultants[position].consultantType.name
        holder.binding.number.text = consultants[position].id.toString()

        holder.itemView.setOnClickListener {
            onItemClick.onItemClickListener(position)
        }
    }

    override fun getItemCount(): Int {
        return consultants.size
    }

    interface OnItemClick {
        fun onItemClickListener(position: Int)
    }
}