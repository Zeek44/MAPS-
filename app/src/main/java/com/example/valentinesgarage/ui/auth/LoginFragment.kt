package com.example.valentinesgarage.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.example.valentinesgarage.R
import com.example.valentinesgarage.viewmodel.AuthViewModel

class LoginFragment : Fragment() {
    private val authViewModel: AuthViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        inflater.inflate(R.layout.fragment_login, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val etUsername = view.findViewById<TextInputEditText>(R.id.etUsername)
        val etPassword = view.findViewById<TextInputEditText>(R.id.etPassword)
        val btnLogin = view.findViewById<Button>(R.id.btnLogin)
        val tvError = view.findViewById<TextView>(R.id.tvError)

        authViewModel.currentUser.observe(viewLifecycleOwner) { user ->
            if (user != null) {
                val dest = when (user.role) {
                    "admin" -> R.id.action_login_to_reports
                    "mechanic" -> R.id.action_login_to_truckList
                    "receptionist" -> R.id.action_login_to_checkIn
                    else -> null
                }
                dest?.let { findNavController().navigate(it) }
            }
        }

        btnLogin.setOnClickListener {
            val username = etUsername.text.toString().trim()
            val password = etPassword.text.toString().trim()
            if (username.isEmpty() || password.isEmpty()) {
                tvError.visibility = View.VISIBLE
                tvError.text = "Please fill in all fields"
                return@setOnClickListener
            }
            authViewModel.login(username, password)
            tvError.visibility = View.GONE

            // Show error if user is null after observing
            authViewModel.currentUser.observe(viewLifecycleOwner) {
                if (it == null) {
                    tvError.visibility = View.VISIBLE
                    tvError.text = "Invalid username or password"
                }
            }
        }
    }
}