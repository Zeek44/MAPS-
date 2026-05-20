package com.example.valentinesgarage.ui.reports

import android.view.*
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.example.valentinesgarage.R
import com.example.valentinesgarage.data.local.entity.User

class PendingUserAdapter(
    private val onApprove: (User) -> Unit,
    private val onReject: (User) -> Unit
) : ListAdapter<User, PendingUserAdapter.ViewHolder>(DIFF_CALLBACK) {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvFullName: TextView = view.findViewById(R.id.tvFullName)
        val tvUsername: TextView = view.findViewById(R.id.tvUsername)
        val tvRole: TextView = view.findViewById(R.id.tvRole)
        val btnApprove: MaterialButton = view.findViewById(R.id.btnApprove)
        val btnReject: MaterialButton = view.findViewById(R.id.btnReject)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_pending_user, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = getItem(position)
        holder.tvFullName.text = user.fullName.ifEmpty { user.username }
        holder.tvUsername.text = "@${user.username}"
        holder.tvRole.text = user.role.replaceFirstChar { it.uppercase() }
        holder.btnApprove.setOnClickListener { onApprove(user) }
        holder.btnReject.setOnClickListener { onReject(user) }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(a: User, b: User) = a.username == b.username
            override fun areContentsTheSame(a: User, b: User) = a == b
        }
    }
}