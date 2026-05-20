package com.example.valentinesgarage.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.example.valentinesgarage.R
import com.example.valentinesgarage.viewmodel.AuthViewModel

class RegisterFragment : Fragment() {
    private val authViewModel: AuthViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        inflater.inflate(R.layout.fragment_register, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val etFullName = view.findViewById<TextInputEditText>(R.id.etFullName)
        val etUsername = view.findViewById<TextInputEditText>(R.id.etUsername)
        val etPassword = view.findViewById<TextInputEditText>(R.id.etPassword)
        val etConfirmPassword = view.findViewById<TextInputEditText>(R.id.etConfirmPassword)
        val rgRole = view.findViewById<RadioGroup>(R.id.rgRole)
        val tvError = view.findViewById<TextView>(R.id.tvError)
        val btnRegister = view.findViewById<MaterialButton>(R.id.btnRegister)
        val btnBack = view.findViewById<ImageButton>(R.id.btnBack)

        btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        btnRegister.setOnClickListener {
            val fullName = etFullName.text.toString().trim()
            val username = etUsername.text.toString().trim()
            val password = etPassword.text.toString().trim()
            val confirmPassword = etConfirmPassword.text.toString().trim()
            val role = when (rgRole.checkedRadioButtonId) {
                R.id.rbReceptionist -> "receptionist"
                R.id.rbMechanic -> "mechanic"
                else -> ""
            }

            // Validation
            when {
                fullName.isEmpty() -> {
                    tvError.visibility = View.VISIBLE
                    tvError.text = "Please enter your full name"
                    return@setOnClickListener
                }
                username.isEmpty() -> {
                    tvError.visibility = View.VISIBLE
                    tvError.text = "Please enter a username"
                    return@setOnClickListener
                }
                password.isEmpty() -> {
                    tvError.visibility = View.VISIBLE
                    tvError.text = "Please enter a password"
                    return@setOnClickListener
                }
                password != confirmPassword -> {
                    tvError.visibility = View.VISIBLE
                    tvError.text = "Passwords do not match"
                    return@setOnClickListener
                }
                role.isEmpty() -> {
                    tvError.visibility = View.VISIBLE
                    tvError.text = "Please select a role"
                    return@setOnClickListener
                }
            }

            tvError.visibility = View.GONE
            authViewModel.register(username, password, role, fullName)
            Toast.makeText(
                requireContext(),
                "Registration submitted! Please wait for admin approval.",
                Toast.LENGTH_LONG
            ).show()
            findNavController().navigateUp()
        }
    }
}