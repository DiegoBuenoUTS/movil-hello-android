package com.bueno.helloandroid.ui.task

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bueno.helloandroid.R
import com.bueno.helloandroid.data.task.Task
import com.bueno.helloandroid.data.task.TaskRepository
import com.bueno.helloandroid.receiver.TaskReminderReceiver

const val ARG_TASK_ID = "task_id"

class TaskDetailFragment : Fragment(R.layout.fragment_task_detail) {

    private lateinit var repository: TaskRepository
    private var currentTask: Task? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        repository = TaskRepository(requireContext())

        val tvFormTitle = view.findViewById<TextView>(R.id.tvFormTitle)
        val etTitle = view.findViewById<EditText>(R.id.etTitle)
        val etDescription = view.findViewById<EditText>(R.id.etDescription)
        val etDate = view.findViewById<EditText>(R.id.etDate)
        val cbReminder = view.findViewById<CheckBox>(R.id.cbReminder)
        val btnSave = view.findViewById<Button>(R.id.btnSave)
        val btnDelete = view.findViewById<Button>(R.id.btnDelete)

        val taskId = arguments?.getInt(ARG_TASK_ID, NO_TASK_ID) ?: NO_TASK_ID
        currentTask = repository.getTaskById(taskId)

        currentTask?.let { task ->
            tvFormTitle.text = "Editar tarea"
            btnSave.text = "Actualizar"
            btnDelete.visibility = View.VISIBLE
            etTitle.setText(task.title)
            etDescription.setText(task.description)
            etDate.setText(task.date)
            cbReminder.isChecked = task.hasReminder
        } ?: run {
            tvFormTitle.text = "Nueva tarea"
            btnDelete.visibility = View.GONE
        }

        btnSave.setOnClickListener {
            val title = etTitle.text.toString().trim()
            val description = etDescription.text.toString().trim()
            val date = etDate.text.toString().trim()
            val reminder = cbReminder.isChecked

            if (title.isBlank()) {
                etTitle.error = "El titulo es obligatorio"
                etTitle.requestFocus()
                return@setOnClickListener
            }

            val task = Task(
                id = currentTask?.id ?: repository.getNextId(),
                title = title,
                description = description,
                date = date,
                hasReminder = reminder
            )

            if (currentTask == null) {
                repository.addTask(task)
            } else {
                repository.updateTask(task)
            }

            if (reminder) {
                scheduleReminder(task)
            } else {
                cancelReminder(task.id)
            }

            val message = if (currentTask == null) "Tarea guardada" else "Tarea actualizada"
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            findNavController().navigateUp()
        }

        btnDelete.setOnClickListener {
            currentTask?.let { task ->
                repository.deleteTask(task.id)
                cancelReminder(task.id)
                Toast.makeText(requireContext(), "Tarea eliminada", Toast.LENGTH_SHORT).show()
                findNavController().navigateUp()
            }
        }
    }

    private fun scheduleReminder(task: Task) {
        val pendingIntent = createReminderPendingIntent(task)
        val alarmManager = requireContext()
            .getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val triggerTime = System.currentTimeMillis() + REMINDER_DELAY_MS

        alarmManager.set(
            AlarmManager.RTC_WAKEUP,
            triggerTime,
            pendingIntent
        )
    }

    private fun cancelReminder(taskId: Int) {
        val pendingIntent = PendingIntent.getBroadcast(
            requireContext(),
            taskId,
            Intent(requireContext(), TaskReminderReceiver::class.java),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val alarmManager = requireContext()
            .getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(pendingIntent)
    }

    private fun createReminderPendingIntent(task: Task): PendingIntent {
        val intent = Intent(requireContext(), TaskReminderReceiver::class.java).apply {
            putExtra(TaskReminderReceiver.EXTRA_TASK_ID, task.id)
            putExtra(TaskReminderReceiver.EXTRA_TASK_TITLE, task.title)
            putExtra(TaskReminderReceiver.EXTRA_TASK_DESCRIPTION, task.description)
        }

        return PendingIntent.getBroadcast(
            requireContext(),
            task.id,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

    private companion object {
        const val NO_TASK_ID = -1
        const val REMINDER_DELAY_MS = 30_000L
    }
}
