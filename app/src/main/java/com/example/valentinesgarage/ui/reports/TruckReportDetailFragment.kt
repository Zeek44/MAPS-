package com.example.valentinesgarage.ui.reports

import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.valentinesgarage.R
import com.example.valentinesgarage.viewmodel.RepairTaskViewModel
import com.example.valentinesgarage.viewmodel.TruckViewModel

class TruckReportDetailFragment : Fragment() {
    private val truckViewModel: TruckViewModel by activityViewModels()
    private val repairTaskViewModel: RepairTaskViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        inflater.inflate(R.layout.fragment_truck_report_detail, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val truckId = arguments?.getInt("truckId") ?: return

        truckViewModel.allTrucks.observe(viewLifecycleOwner) { trucks ->
            val truck = trucks.find { it.id == truckId } ?: return@observe
            view.findViewById<TextView>(R.id.tvDetailRegNumber).text = truck.registrationNumber
            view.findViewById<TextView>(R.id.tvDetailOwner).text = "Owner: ${truck.ownerName}"
            view.findViewById<TextView>(R.id.tvDetailKm).text = "${truck.kilometersDriven} km"
            view.findViewById<TextView>(R.id.tvDetailConditionRating).text = "${truck.conditionRating}/5"
            view.findViewById<TextView>(R.id.tvDetailCheckedBy).text = truck.checkedInBy
            view.findViewById<TextView>(R.id.tvDetailConditionNotes).text = truck.conditionDescription
        }

        repairTaskViewModel.getTasksForTruck(truckId).observe(viewLifecycleOwner) { tasks ->
            val tvTasks = view.findViewById<TextView>(R.id.tvDetailTasks)
            val tvNoTasks = view.findViewById<TextView>(R.id.tvNoTasks)

            if (tasks.isEmpty()) {
                tvTasks.visibility = View.GONE
                tvNoTasks.visibility = View.VISIBLE
            } else {
                tvTasks.visibility = View.VISIBLE
                tvNoTasks.visibility = View.GONE
                val sb = StringBuilder()
                tasks.forEach { task ->
                    val status = if (task.isCompleted) "✅" else "⏳"
                    sb.append("$status ${task.taskName}\n")
                    if (task.isCompleted) {
                        sb.append("   Done by: ${task.completedBy}\n")
                    }
                    if (task.notes.isNotEmpty()) {
                        sb.append("   Notes: ${task.notes}\n")
                    }
                    sb.append("\n")
                }
                tvTasks.text = sb.toString().trimEnd()
            }
        }
    }
}