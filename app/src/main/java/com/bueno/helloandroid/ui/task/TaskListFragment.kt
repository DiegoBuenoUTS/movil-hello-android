package com.bueno.helloandroid.ui.task

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bueno.helloandroid.R
import com.bueno.helloandroid.data.task.TaskRepository

class TaskListFragment : Fragment(R.layout.fragment_task_list) {

    private lateinit var repository: TaskRepository
    private lateinit var adapter: TaskAdapter
    private lateinit var emptyState: TextView
    private lateinit var taskCount: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        repository = TaskRepository(requireContext())

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerTasks)
        val btnAdd = view.findViewById<Button>(R.id.btnAddTask)
        emptyState = view.findViewById(R.id.tvEmptyState)
        taskCount = view.findViewById(R.id.tvTaskCount)

        adapter = TaskAdapter { task ->
            val args = Bundle().apply {
                putInt(ARG_TASK_ID, task.id)
            }
            findNavController().navigate(
                R.id.action_taskListFragment_to_taskDetailFragment,
                args
            )
        }

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter

        loadTasks()

        btnAdd.setOnClickListener {
            findNavController().navigate(
                R.id.action_taskListFragment_to_taskDetailFragment
            )
        }
    }

    override fun onResume() {
        super.onResume()
        loadTasks() // recarga al volver del detalle
    }

    private fun loadTasks() {
        val tasks = repository.getAllTasks()
        adapter.submitList(tasks)
        emptyState.visibility = if (tasks.isEmpty()) View.VISIBLE else View.GONE
        taskCount.text = when (tasks.size) {
            0 -> "Sin tareas guardadas"
            1 -> "1 tarea guardada"
            else -> "${tasks.size} tareas guardadas"
        }
    }
}
