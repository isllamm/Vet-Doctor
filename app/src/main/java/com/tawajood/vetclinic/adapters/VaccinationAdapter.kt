package com.tawajood.vetclinic.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tawajood.vetclinic.databinding.ItemMedicineBinding
import com.tawajood.vetclinic.databinding.ItemVaccinationBinding
import com.tawajood.vetclinic.pojo.PreviousRequests
import com.tawajood.vetclinic.pojo.Vaccination

class VaccinationAdapter :
    RecyclerView.Adapter<VaccinationAdapter.VaccinationViewHolder>() {

    var vaccination = mutableListOf<Vaccination>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class VaccinationViewHolder(val binding: ItemVaccinationBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): VaccinationAdapter.VaccinationViewHolder {
        val binding =
            ItemVaccinationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VaccinationAdapter.VaccinationViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: VaccinationAdapter.VaccinationViewHolder,
        position: Int
    ) {

        holder.binding.tvDate.text = vaccination[position].date
        holder.binding.tvVacc.text = vaccination[position].vaccinationType
    }

    override fun getItemCount(): Int {
        return vaccination.size
    }

}