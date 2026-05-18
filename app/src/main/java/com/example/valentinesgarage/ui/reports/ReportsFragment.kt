package com.example.valentinesgarage.ui.reports

import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.valentinesgarage.R
import com.example.valentinesgarage.viewmodel.RepairTaskViewModel
import com.example.valentinesgarage.viewmodel.TruckViewModel

class ReportsFragment : Fragment() {
    private val truckViewModel: TruckViewModel by activityViewModels()
    private val repairTaskViewModel: RepairTaskViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        inflater.inflate(R.layout.fragment_reports, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val rvReports = view.findViewById<RecyclerView>(R.id.rvReports)
        rvReports.layoutManager = LinearLayoutManager(requireContext())
        rvReports.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))

        val reportLines = mutableListOf<String>()

        truckViewModel.allTrucks.observe(viewLifecycleOwner) { trucks ->
            reportLines.clear()
            trucks.forEach { truck ->
                reportLines.add("🚛 ${truck.registrationNumber} | Owner: ${truck.ownerName} | KM: ${truck.kilometersDriven} | Condition: ${truck.conditionRating}/5")
                reportLines.add("   Checked in by: ${truck.checkedInBy}")
                reportLines.add("   Notes: ${truck.conditionDescription}")
                reportLines.add("---")
            }
            rvReports.adapter = ReportAdapter(reportLines)
        }

        repairTaskViewModel.getAllTasks().observe(viewLifecycleOwner) { tasks ->
            val taskLines = mutableListOf<String>()
            taskLines.add("=== TASK REPORTS ===")
            tasks.forEach { task ->
                val status = if (task.isCompleted) "✅ Done by: ${task.completedBy}" else "⏳ Pending"
                taskLines.add("• ${task.taskName} — $status")
                if (task.notes.isNotEmpty()) taskLines.add("  Notes: ${task.notes}")
            }
            reportLines.addAll(taskLines)
            rvReports.adapter = ReportAdapter(reportLines)
        }
    }
}