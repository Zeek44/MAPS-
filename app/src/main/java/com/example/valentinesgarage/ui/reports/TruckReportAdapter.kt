package com.example.valentinesgarage.ui.reports

import android.view.*
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.valentinesgarage.R
import com.example.valentinesgarage.data.local.entity.Truck

class TruckReportAdapter(
    private val onTruckClick: (Truck) -> Unit
) : ListAdapter<Truck, TruckReportAdapter.ViewHolder>(DIFF_CALLBACK) {

    var taskCountMap: Map<Int, Pair<Int, Int>> = emptyMap()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvRegNumber: TextView = view.findViewById(R.id.tvReportRegNumber)
        val tvOwner: TextView = view.findViewById(R.id.tvReportOwner)
        val tvCondition: TextView = view.findViewById(R.id.tvReportCondition)
        val tvKm: TextView = view.findViewById(R.id.tvReportKm)
        val tvCheckedBy: TextView = view.findViewById(R.id.tvReportCheckedBy)
        val tvTasks: TextView = view.findViewById(R.id.tvReportTasks)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_report_truck, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val truck = getItem(position)
        holder.tvRegNumber.text = truck.registrationNumber
        holder.tvOwner.text = "Owner: ${truck.ownerName}"
        holder.tvCondition.text = "${truck.conditionRating}/5"
        holder.tvKm.text = "${truck.kilometersDriven} km"
        holder.tvCheckedBy.text = truck.checkedInBy
        val tasks = taskCountMap[truck.id]
        if (tasks != null) {
            holder.tvTasks.text = "✅ ${tasks.first} done / ${tasks.second} total tasks"
        } else {
            holder.tvTasks.text = "No tasks added yet"
        }
        holder.itemView.setOnClickListener { onTruckClick(truck) }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Truck>() {
            override fun areItemsTheSame(a: Truck, b: Truck) = a.id == b.id
            override fun areContentsTheSame(a: Truck, b: Truck) = a == b
        }
    }
}