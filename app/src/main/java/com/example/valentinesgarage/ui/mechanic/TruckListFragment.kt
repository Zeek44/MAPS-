package com.example.valentinesgarage.ui.mechanic

import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.valentinesgarage.R
import com.example.valentinesgarage.viewmodel.TruckViewModel

class TruckListFragment : Fragment() {
    private val truckViewModel: TruckViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        inflater.inflate(R.layout.fragment_truck_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val rvTrucks = view.findViewById<RecyclerView>(R.id.rvTrucks)
        val tvNoTrucks = view.findViewById<TextView>(R.id.tvNoTrucks)
        rvTrucks.layoutManager = LinearLayoutManager(requireContext())

        val adapter = TruckAdapter { truck ->
            val bundle = Bundle().apply {
                putInt("truckId", truck.id)
                putString("truckReg", truck.registrationNumber)
            }
            findNavController().navigate(R.id.action_truckList_to_tasks, bundle)
        }
        rvTrucks.adapter = adapter

        truckViewModel.allTrucks.observe(viewLifecycleOwner) { trucks ->
            adapter.submitList(trucks)
            tvNoTrucks.visibility = if (trucks.isEmpty()) View.VISIBLE else View.GONE
        }
    }
}