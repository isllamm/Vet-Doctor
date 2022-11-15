package com.tawajood.vetclinic.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tawajood.vetclinic.databinding.ItemNotificationBinding
import com.tawajood.vetclinic.databinding.ItemPreviousReportBinding
import com.tawajood.vetclinic.pojo.NotificationData
import com.tawajood.vetclinic.pojo.PreviousRequests

class PreviousReportsAdapter :
    RecyclerView.Adapter<PreviousReportsAdapter.PreviousReportsViewHolder>() {

    var previousRequests = mutableListOf<PreviousRequests>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class PreviousReportsViewHolder(val binding: ItemPreviousReportBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PreviousReportsAdapter.PreviousReportsViewHolder {
        val binding =
            ItemPreviousReportBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PreviousReportsAdapter.PreviousReportsViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: PreviousReportsAdapter.PreviousReportsViewHolder,
        position: Int
    ) {
        Glide.with(holder.itemView.context).load(previousRequests[position].clinic.image)
            .into(holder.binding.ivClinic)
        holder.binding.tvClinicName.text = previousRequests[position].clinic.name
        holder.binding.tvDate.text = previousRequests[position].date
        holder.binding.tvReport.text = previousRequests[position].clinic_report
    }

    override fun getItemCount(): Int {
        return previousRequests.size
    }

}