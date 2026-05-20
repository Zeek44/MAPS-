package com.example.valentinesgarage.ui.reports

import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.valentinesgarage.R
import com.example.valentinesgarage.viewmodel.AuthViewModel

class AdminApprovalFragment : Fragment() {
    private val authViewModel: AuthViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        inflater.inflate(R.layout.fragment_admin_approval, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val rvPendingUsers = view.findViewById<RecyclerView>(R.id.rvPendingUsers)
        val tvNoPending = view.findViewById<TextView>(R.id.tvNoPending)
        rvPendingUsers.layoutManager = LinearLayoutManager(requireContext())

        val adapter = PendingUserAdapter(
            onApprove = { user -> authViewModel.approveUser(user.username) },
            onReject = { user -> authViewModel.rejectUser(user.username) }
        )
        rvPendingUsers.adapter = adapter

        authViewModel.getPendingUsers().observe(viewLifecycleOwner) { users ->
            adapter.submitList(users)
            tvNoPending.visibility = if (users.isEmpty()) View.VISIBLE else View.GONE
        }
    }
}