package com.example.valentinesgarage.ui.mechanic

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.example.valentinesgarage.R
import com.example.valentinesgarage.data.local.entity.RepairTask

class TaskAdapter(
    private val mechanicUsername: String,
    private val onTaskUpdated: (RepairTask) -> Unit
) : ListAdapter<RepairTask, TaskAdapter.TaskViewHolder>(DIFF_CALLBACK) {

    inner class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cbTask: CheckBox = view.findViewById(R.id.cbTask)
        val tvTaskName: TextView = view.findViewById(R.id.tvTaskName)
        val tvCompletedBy: TextView = view.findViewById(R.id.tvCompletedBy)
        val etNotes: TextInputEditText = view.findViewById(R.id.etNotes)
        val btnSaveNotes: Button = view.findViewById(R.id.btnSaveNotes)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        TaskViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false))

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = getItem(position)
        holder.tvTaskName.text = task.taskName
        holder.cbTask.isChecked = task.isCompleted
        holder.etNotes.setText(task.notes)
        holder.tvCompletedBy.text = if (task.isCompleted) "Done by: ${task.completedBy}" else ""

        holder.cbTask.setOnCheckedChangeListener { _, isChecked ->
            onTaskUpdated(task.copy(
                isCompleted = isChecked,
                completedBy = if (isChecked) mechanicUsername else "",
                completedAt = if (isChecked) System.currentTimeMillis() else null
            ))
        }

        holder.btnSaveNotes.setOnClickListener {
            onTaskUpdated(task.copy(notes = holder.etNotes.text.toString()))
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<RepairTask>() {
            override fun areItemsTheSame(a: RepairTask, b: RepairTask) = a.id == b.id
            override fun areContentsTheSame(a: RepairTask, b: RepairTask) = a == b
        }
    }
}