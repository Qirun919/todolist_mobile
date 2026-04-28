package com.example.todolist.model

import java.sql.Date

data class TodoDetails (
    val id: Double,
    val title: String,
    val meaning: String? = null,
    val synonyms: String? = null,
    val details: String,
    val status: ToDoStatus,
    val date: Date? = null
)

enum class ToDoStatus {
    NEW,
    DONE
}