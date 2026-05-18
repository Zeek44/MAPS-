package com.example.valentinesgarage.ui.checkin

import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.RatingBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.textfield.TextInputEditText
import com.example.valentinesgarage.R
import com.example.valentinesgarage.data.local.entity.Truck
import com.example.valentinesgarage.viewmodel.AuthViewModel
import com.example.valentinesgarage.viewmodel.TruckViewModel

class CheckInFragment : Fragment() {
    private val truckViewModel: TruckViewModel by activityViewModels()
    private val authViewModel: AuthViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        inflater.inflate(R.layout.fragment_check_in, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val etReg = view.findViewById<TextInputEditText>(R.id.etRegNumber)
        val etOwner = view.findViewById<TextInputEditText>(R.id.etOwnerName)
        val etKm = view.findViewById<TextInputEditText>(R.id.etKilometers)
        val etCondition = view.findViewById<TextInputEditText>(R.id.etCondition)
        val ratingBar = view.findViewById<RatingBar>(R.id.ratingBar)
        val btnCheckIn = view.findViewById<Button>(R.id.btnCheckIn)

        btnCheckIn.setOnClickListener {
            val reg = etReg.text.toString().trim()
            val owner = etOwner.text.toString().trim()
            val km = etKm.text.toString().toDoubleOrNull()
            val condition = etCondition.text.toString().trim()
            val rating = ratingBar.rating.toInt()
            val user = authViewModel.currentUser.value?.username ?: "unknown"

            if (reg.isEmpty() || owner.isEmpty() || km == null || condition.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val truck = Truck(
                registrationNumber = reg,
                ownerName = owner,
                conditionDescription = condition,
                conditionRating = rating,
                kilometersDriven = km,
                checkedInBy = user
            )
            truckViewModel.insertTruck(truck)
            Toast.makeText(requireContext(), "Truck $reg checked in!", Toast.LENGTH_SHORT).show()
            // Clear form
            etReg.text?.clear(); etOwner.text?.clear()
            etKm.text?.clear(); etCondition.text?.clear()
            ratingBar.rating = 0f
        }
    }
}