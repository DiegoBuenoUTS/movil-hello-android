package com.bueno.helloandroid.data.task

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class TaskRepository(context: Context) {
    companion object {
        private const val PREFS_NAME = "tasks_prefs"
        private const val KEY_TASK_LIST = "task_list"
    }

    private val prefs: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    private val gson = Gson()
    private var tasksInMemory: MutableList<Task> = loadTasksFromPrefs()

    fun getAllTasks(): List<Task> = tasksInMemory.toList()

    fun getTaskById(taskId: Int): Task? = tasksInMemory.firstOrNull { it.id == taskId }

    fun getNextId(): Int = (tasksInMemory.maxOfOrNull { it.id } ?: 0) + 1

    fun addTask(task: Task) {
        tasksInMemory.add(task)
        saveTasksToPrefs()
    }

    fun updateTask(updated: Task) {
        val index = tasksInMemory.indexOfFirst { it.id == updated.id }
        if (index != -1) {
            tasksInMemory[index] = updated
            saveTasksToPrefs()
        }
    }

    fun deleteTask(taskId: Int) {
        tasksInMemory.removeAll { it.id == taskId }
        saveTasksToPrefs()
    }

    private fun loadTasksFromPrefs(): MutableList<Task> {
        val json = prefs.getString(KEY_TASK_LIST, null) ?: return mutableListOf()
        val type = object : TypeToken<List<Task>>() {}.type
        return try {
            val list: List<Task> = gson.fromJson(json, type)
            list.toMutableList()
        } catch (e: Exception) {
            e.printStackTrace()
            mutableListOf()
        }
    }

    private fun saveTasksToPrefs() {
        val editor = prefs.edit()
        val json = gson.toJson(tasksInMemory)
        editor.putString(KEY_TASK_LIST, json)
        editor.apply()
    }
}
