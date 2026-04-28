package com.example.todolist.fragments

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.database.TodoDatabase
import com.example.todolist.R
import com.example.todolist.RecyclerViewAdapters.TodoRecycleViewAdapter
import com.example.todolist.model.TodoEntity
import kotlinx.coroutines.launch

class CompletedWordFragment : Fragment(R.layout.completed_word_fragment) {

    private var sortOrder = "ascending"
    private var sortBy = "title"
    private var searchQuery = ""

    private lateinit var recyclerView: RecyclerView
    private lateinit var emptyStateLayout: LinearLayout
    private var data: List<TodoEntity> = emptyList()

    private lateinit var dao: com.example.todolist.database.TodoDao

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerView)
        emptyStateLayout = view.findViewById(R.id.emptyStateLayout)

        dao = TodoDatabase.getDatabase(requireContext()).todoDao()

        loadData()

        // 监听返回栈变化
        requireActivity().supportFragmentManager.addOnBackStackChangedListener {
            val container = requireActivity().findViewById<View>(R.id.detailsContainer)
            if (container.visibility == View.GONE) {
                loadData()
            }
        }

        // Search
        val searchInput = view.findViewById<EditText>(R.id.editTextText2)
        searchInput.addTextChangedListener { text ->
            searchQuery = text.toString().trim()
            applyFilterAndSort()
        }

        // Sort button
        val btnSort = view.findViewById<ImageButton>(R.id.imageButton3)
        btnSort.setOnClickListener {
            val dialog = SortDialogFragment(sortOrder, sortBy) { newSortOrder, newSortBy ->
                sortOrder = newSortOrder
                sortBy = newSortBy
                applyFilterAndSort()
            }
            dialog.show(parentFragmentManager, "SortDialog")
        }
    }

    override fun onResume() {
        super.onResume()
        if (::dao.isInitialized) {
            loadData()
        }
    }

    fun loadData() {
        lifecycleScope.launch {
            data = dao.getByStatus("DONE")
            applyFilterAndSort()
        }
    }

    private fun applyFilterAndSort() {
        val filtered = if (searchQuery.isEmpty()) {
            data
        } else {
            data.filter {
                it.title.contains(searchQuery, ignoreCase = true)
            }
        }

        val sorted = if (sortBy == "title") {
            filtered.sortedBy { it.title.lowercase() }
        } else {
            filtered.sortedBy { it.title }
        }

        val finalList = if (sortOrder == "ascending") sorted else sorted.reversed()

        updateList(finalList)
    }

    private fun updateList(list: List<TodoEntity>) {
        if (list.isEmpty()) {
            recyclerView.visibility = View.GONE
            emptyStateLayout.visibility = View.VISIBLE
        } else {
            recyclerView.visibility = View.VISIBLE
            emptyStateLayout.visibility = View.GONE
            recyclerView.layoutManager = LinearLayoutManager(view?.context)
            recyclerView.adapter = TodoRecycleViewAdapter(list) { item ->
                val detailFragment = WordDetailsFragment().apply {
                    arguments = Bundle().apply {
                        putInt("id", item.id)
                        putString("title", item.title)
                        putString("meaning", item.meaning)
                        putString("synonyms", item.synonyms)
                        putString("details", item.details)
                        putString("status", item.status)
                    }
                }
                requireActivity().findViewById<View>(R.id.detailsContainer).visibility = View.VISIBLE
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.detailsContainer, detailFragment)
                    .addToBackStack(null)
                    .commit()
            }
        }
    }
}