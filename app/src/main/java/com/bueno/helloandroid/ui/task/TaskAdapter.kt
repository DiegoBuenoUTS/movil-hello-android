package com.bueno.helloandroid.ui.task

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bueno.helloandroid.R
import com.bueno.helloandroid.data.task.Task

class TaskAdapter(
    private val onTaskClick: (Task) -> Unit
) : ListAdapter<Task, TaskAdapter.TaskViewHolder>(TaskDiffCallback()) {

    class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.taskTitle)
        val description: TextView = view.findViewById(R.id.taskDescription)
        val date: TextView = view.findViewById(R.id.taskDate)
        val reminder: TextView = view.findViewById(R.id.taskReminder)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = getItem(position)
        holder.title.text = task.title
        holder.description.text = task.description.ifBlank { "Sin descripcion" }
        holder.date.text = task.date.ifBlank { "Sin fecha" }
        holder.reminder.text = if (task.hasReminder) "Recordatorio activo" else "Sin recordatorio"
        holder.itemView.setOnClickListener { onTaskClick(task) }
    }

    private class TaskDiffCallback : DiffUtil.ItemCallback<Task>() {
        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem == newItem
        }
    }
}
