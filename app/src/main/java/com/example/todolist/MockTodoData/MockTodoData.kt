package com.example.todolist.MockTodoData

import com.example.todolist.model.ToDoStatus
import com.example.todolist.model.TodoDetails

object MockTodoData {
    val dataList: MutableList<TodoDetails> = mutableListOf(
        TodoDetails(1.0, "Task 1", details = "Details for task 1", status = ToDoStatus.NEW),
        TodoDetails(2.0, "Task 2", details = "Details for task 2", status = ToDoStatus.DONE),
        TodoDetails(3.0, "Task 3", details = "Details for task 3", status = ToDoStatus.NEW),
        TodoDetails(4.0, "Task 4", details = "Details for task 4", status = ToDoStatus.DONE),
        TodoDetails(5.0, "Task 5", details = "Details for task 5", status = ToDoStatus.NEW),
        TodoDetails(6.0, "Task 6", details = "Details for task 6", status = ToDoStatus.DONE),
        TodoDetails(7.0, "Task 7", details = "Details for task 7", status = ToDoStatus.NEW),
        TodoDetails(8.0, "Task 8", details = "Details for task 8", status = ToDoStatus.DONE),
        TodoDetails(9.0, "Task 9", details = "Details for task 9", status = ToDoStatus.NEW),
        TodoDetails(10.0, "Task 10", details = "Details for task 10", status = ToDoStatus.DONE)
    )

    fun populateData(): MutableList<TodoDetails> = dataList
}