package com.example.todolist.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioGroup
import androidx.fragment.app.DialogFragment
import com.example.todolist.R

class SortDialogFragment(
    private val currentSortOrder: String,
    private val currentSortBy: String,
    private val onDone: (sortOrder: String, sortBy: String) -> Unit
) : DialogFragment() {

    private var selectedSortOrder = currentSortOrder
    private var selectedSortBy = currentSortBy

    private lateinit var rgSortOrder: RadioGroup
    private lateinit var rgSortBy: RadioGroup
    private lateinit var btnDone: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.dialog_sort, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Find views
        rgSortOrder = view.findViewById(R.id.rgSortOrder)
        rgSortBy = view.findViewById(R.id.rgSortBy)
        btnDone = view.findViewById(R.id.btnDone)

        // Set initial selections
        if (currentSortOrder == "ascending") {
            rgSortOrder.check(R.id.rbAscending)
        } else {
            rgSortOrder.check(R.id.rbDescending)
        }

        if (currentSortBy == "title") {
            rgSortBy.check(R.id.rbTitle)
        } else {
            rgSortBy.check(R.id.rbDate)
        }

        // Listeners
        rgSortOrder.setOnCheckedChangeListener { _, checkedId ->
            selectedSortOrder = if (checkedId == R.id.rbAscending) "ascending" else "descending"
        }

        rgSortBy.setOnCheckedChangeListener { _, checkedId ->
            selectedSortBy = if (checkedId == R.id.rbTitle) "title" else "date"
        }

        btnDone.setOnClickListener {
            onDone(selectedSortOrder, selectedSortBy)
            dismiss()
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            (resources.displayMetrics.widthPixels * 0.85).toInt(),
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
    }
}