package com.example.valentinesgarage.ui.reports

import android.view.*
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ReportAdapter(private val items: List<String>) : RecyclerView.Adapter<ReportAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvLine: TextView = view.findViewById(android.R.id.text1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_1, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvLine.text = items[position]
    }

    override fun getItemCount() = items.size
}