package com.tawajood.vetclinic.adapters

import android.annotation.SuppressLint
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.tawajood.vetclinic.R
import com.tawajood.vetclinic.databinding.ItemBodyPartsBinding
import com.tawajood.vetclinic.pojo.BodyPart

class BodyPartsAdapter :
    RecyclerView.Adapter<BodyPartsAdapter.BodyPartsViewHolder>() {

    var bodyParts = mutableListOf<BodyPart>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    @SuppressLint("NotifyDataSetChanged")
    private fun setStatus(position: Int, status: Int) {
        bodyParts[position].status = status
        notifyDataSetChanged()
    }

    fun getIds(): ArrayList<Int> {
        val ids = ArrayList<Int>()
        bodyParts.forEach { bodyParts ->
            ids.add(bodyParts.status)
        }
        return ids
    }

    class BodyPartsViewHolder(val binding: ItemBodyPartsBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BodyPartsAdapter.BodyPartsViewHolder {
        val binding =
            ItemBodyPartsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BodyPartsAdapter.BodyPartsViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: BodyPartsAdapter.BodyPartsViewHolder,
        position: Int
    ) {
        holder.binding.tvBodyPart.text = bodyParts[position].name


        //fit
        holder.binding.frameFit.setOnClickListener {
            setStatus(position, 1)
            holder.binding.frameFit.background =
                ContextCompat.getDrawable(
                    holder.itemView.context,
                    R.drawable.full_border_radius_12_blue
                )
            holder.binding.tvFit.setTextColor(Color.parseColor("#FFFFFFFF"))

            holder.binding.frameUnfit.background =
                ContextCompat.getDrawable(holder.itemView.context, R.drawable.full_border_radius_12)
            holder.binding.tvUnfit.setTextColor(Color.parseColor("#2D4B50"))
        }


        //unfit
        holder.binding.frameUnfit.setOnClickListener {
            setStatus(position, 0)
            holder.binding.frameUnfit.background =
                ContextCompat.getDrawable(
                    holder.itemView.context,
                    R.drawable.full_border_radius_12_blue
                )
            holder.binding.tvUnfit.setTextColor(Color.parseColor("#FFFFFFFF"))

            holder.binding.frameFit.background =
                ContextCompat.getDrawable(holder.itemView.context, R.drawable.full_border_radius_12)
            holder.binding.tvFit.setTextColor(Color.parseColor("#2D4B50"))
        }
    }

    override fun getItemCount(): Int {
        return bodyParts.size
    }

}