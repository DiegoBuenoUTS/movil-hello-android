package com.bueno.helloandroid.data.task

data class Task(
    val id: Int,
    val title: String,
    val description: String,
    val date: String,
    val hasReminder: Boolean
)