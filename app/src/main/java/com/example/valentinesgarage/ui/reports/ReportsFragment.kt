package com.example.valentinesgarage.ui.reports

import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
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
        val tvNoReports = view.findViewById<TextView>(R.id.tvNoReports)
        rvReports.layoutManager = LinearLayoutManager(requireContext())

        view.findViewById<MaterialButton>(R.id.btnApprovals).setOnClickListener {
            findNavController().navigate(R.id.action_reports_to_approval)
        }

        val adapter = TruckReportAdapter { truck ->
            val bundle = Bundle().apply {
                putInt("truckId", truck.id)
            }
            findNavController().navigate(R.id.action_reports_to_detail, bundle)
        }
        rvReports.adapter = adapter

        truckViewModel.allTrucks.observe(viewLifecycleOwner) { trucks ->
            adapter.submitList(trucks)
            tvNoReports.visibility = if (trucks.isEmpty()) View.VISIBLE else View.GONE
        }

        repairTaskViewModel.getAllTasks().observe(viewLifecycleOwner) { tasks ->
            val taskCountMap = tasks.groupBy { it.truckId }.mapValues { entry ->
                val done = entry.value.count { it.isCompleted }
                val total = entry.value.size
                Pair(done, total)
            }
            adapter.taskCountMap = taskCountMap
        }
    }
}