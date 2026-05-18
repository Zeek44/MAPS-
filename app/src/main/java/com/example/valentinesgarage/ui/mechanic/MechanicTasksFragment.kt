package com.example.valentinesgarage.ui.mechanic

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.valentinesgarage.R
import com.example.valentinesgarage.data.local.entity.RepairTask
import com.example.valentinesgarage.viewmodel.AuthViewModel
import com.example.valentinesgarage.viewmodel.RepairTaskViewModel

class MechanicTasksFragment : Fragment() {
    private val repairTaskViewModel: RepairTaskViewModel by activityViewModels()
    private val authViewModel: AuthViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        inflater.inflate(R.layout.fragment_mechanic_tasks, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val truckId = arguments?.getInt("truckId") ?: return
        val truckReg = arguments?.getString("truckReg") ?: ""
        val currentUser = authViewModel.currentUser.value?.username ?: ""

        view.findViewById<TextView>(R.id.tvTruckInfo).text = "Truck: $truckReg"

        val rvTasks = view.findViewById<RecyclerView>(R.id.rvTasks)
        rvTasks.layoutManager = LinearLayoutManager(requireContext())

        val adapter = TaskAdapter(currentUser) { updatedTask ->
            repairTaskViewModel.updateTask(updatedTask)
        }
        rvTasks.adapter = adapter

        repairTaskViewModel.getTasksForTruck(truckId).observe(viewLifecycleOwner) { tasks ->
            adapter.submitList(tasks)
        }

        view.findViewById<Button>(R.id.btnAddTask).setOnClickListener {
            val input = EditText(requireContext())
            input.hint = "Task name"
            AlertDialog.Builder(requireContext())
                .setTitle("Add Task")
                .setView(input)
                .setPositiveButton("Add") { _, _ ->
                    val taskName = input.text.toString().trim()
                    if (taskName.isNotEmpty()) {
                        repairTaskViewModel.insertTask(
                            RepairTask(truckId = truckId, taskName = taskName)
                        )
                    }
                }
                .setNegativeButton("Cancel", null)
                .show()
        }
    }
}