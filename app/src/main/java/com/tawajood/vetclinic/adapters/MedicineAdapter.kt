package com.tawajood.vetclinic.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tawajood.vetclinic.databinding.ItemMedicineBinding
import com.tawajood.vetclinic.databinding.ItemPreviousReportBinding
import com.tawajood.vetclinic.pojo.PreviousRequests

class MedicineAdapter :
    RecyclerView.Adapter<MedicineAdapter.MedicineViewHolder>() {

    var previousRequests = mutableListOf<PreviousRequests>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class MedicineViewHolder(val binding: ItemMedicineBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MedicineAdapter.MedicineViewHolder {
        val binding =
            ItemMedicineBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MedicineAdapter.MedicineViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: MedicineAdapter.MedicineViewHolder,
        position: Int
    ) {
        Glide.with(holder.itemView.context).load(previousRequests[position].clinic.image)
            .into(holder.binding.ivClinic)
        holder.binding.tvClinicName.text = previousRequests[position].clinic.name
        holder.binding.tvDate.text = previousRequests[position].date
        holder.binding.tvReport.text = previousRequests[position].medicines[0].medicine
    }

    override fun getItemCount(): Int {
        return previousRequests.size
    }

}