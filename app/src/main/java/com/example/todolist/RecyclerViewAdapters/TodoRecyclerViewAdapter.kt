package com.example.todolist.RecyclerViewAdapters

import com.example.todolist.model.TodoEntity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R

class TodoRecycleViewAdapter(
    private val todoDetails: List<TodoEntity>,
    private val onItemClick: (TodoEntity) -> Unit
) : RecyclerView.Adapter<TodoRecycleViewAdapter.TodoViewHolder>() {

    inner class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.textViewTitle)
        val detailTextView: TextView = itemView.findViewById(R.id.textViewDetails)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.todo_item, parent, false)
        return TodoViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val todoDetail = todoDetails[position]
        holder.titleTextView.text = todoDetail.title
        holder.detailTextView.text = todoDetail.details
        holder.itemView.setOnClickListener {
            onItemClick(todoDetail)
        }
    }

    override fun getItemCount(): Int = todoDetails.size
}