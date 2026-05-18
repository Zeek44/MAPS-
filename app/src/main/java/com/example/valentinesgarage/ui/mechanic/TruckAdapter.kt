package com.example.valentinesgarage.ui.mechanic

import android.view.*
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.valentinesgarage.R
import com.example.valentinesgarage.data.local.entity.Truck

class TruckAdapter(private val onTruckClick: (Truck) -> Unit) :
    ListAdapter<Truck, TruckAdapter.TruckViewHolder>(DIFF_CALLBACK) {

    inner class TruckViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvRegNumber: TextView = view.findViewById(R.id.tvRegNumber)
        val tvOwnerName: TextView = view.findViewById(R.id.tvOwnerName)
        val tvKilometers: TextView = view.findViewById(R.id.tvKilometers)
        val tvCondition: TextView = view.findViewById(R.id.tvCondition)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        TruckViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_truck, parent, false))

    override fun onBindViewHolder(holder: TruckViewHolder, position: Int) {
        val truck = getItem(position)
        holder.tvRegNumber.text = truck.registrationNumber
        holder.tvOwnerName.text = "Owner: ${truck.ownerName}"
        holder.tvKilometers.text = "KM: ${truck.kilometersDriven}"
        holder.tvCondition.text = "Condition: ${truck.conditionRating}/5 — ${truck.conditionDescription}"
        holder.itemView.setOnClickListener { onTruckClick(truck) }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Truck>() {
            override fun areItemsTheSame(a: Truck, b: Truck) = a.id == b.id
            override fun areContentsTheSame(a: Truck, b: Truck) = a == b
        }
    }
}