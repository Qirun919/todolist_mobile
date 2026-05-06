package com.example.todolist.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.todolist.R
import com.example.todolist.database.TodoDatabase
import com.example.todolist.model.TodoEntity
import kotlinx.coroutines.launch

class WordDetailsFragment : Fragment(R.layout.word_details) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dao = TodoDatabase.getDatabase(requireContext()).todoDao()

        val id       = arguments?.getInt("id", -1) ?: -1
        val title    = arguments?.getString("title")
        val meaning  = arguments?.getString("meaning")
        val synonyms = arguments?.getString("synonyms")
        val details  = arguments?.getString("details")
        val status   = arguments?.getString("status") ?: "NEW"

        view.findViewById<TextView>(R.id.tvTitle).text   = title
        view.findViewById<TextView>(R.id.tvMeaning).text = meaning
        view.findViewById<TextView>(R.id.tvSynonym).text = synonyms
        view.findViewById<TextView>(R.id.tvDetails).text = details

        // Hide Update and Done buttons if word is already completed
        if (status == "DONE") {
            view.findViewById<Button>(R.id.btnUpdate).visibility = View.GONE
            view.findViewById<Button>(R.id.btnDone).visibility = View.GONE
        }

        // UPDATE
        view.findViewById<Button>(R.id.btnUpdate).setOnClickListener {
            val updateFragment = AddWordFragment().apply {
                arguments = Bundle().apply {
                    putInt("id",          id)
                    putString("title",    title)
                    putString("meaning",  meaning)
                    putString("synonyms", synonyms)
                    putString("details",  details)
                }
            }
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.detailsContainer, updateFragment)
                .addToBackStack(null)
                .commit()
        }

        // DELETE
        view.findViewById<Button>(R.id.btnDelete).setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("Are you sure?")
                .setMessage("Delete this word permanently?")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Delete") { _, _ ->
                    lifecycleScope.launch {
                        dao.delete(
                            TodoEntity(
                                id       = id,
                                title    = title ?: "",
                                meaning  = meaning,
                                synonyms = synonyms,
                                details  = details ?: "",
                                status   = status
                            )
                        )
                    }
                    // 隐藏并禁用点击
                    val container = requireActivity().findViewById<View>(R.id.detailsContainer)
                    container.visibility = View.GONE
                    container.isClickable = false
                    container.isFocusable = false
                    requireActivity().supportFragmentManager.popBackStack()
                }
                .show()
        }

        // DONE
        view.findViewById<Button>(R.id.btnDone).setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("Are you sure?")
                .setMessage("Do you want to move this word to completed list?")
                .setPositiveButton("Yes") { _, _ ->
                    lifecycleScope.launch {
                        dao.update(
                            TodoEntity(
                                id       = id,
                                title    = title ?: "",
                                meaning  = meaning,
                                synonyms = synonyms,
                                details  = details ?: "",
                                status   = "DONE"
                            )
                        )
                    }
                    // 隐藏并禁用点击
                    val container = requireActivity().findViewById<View>(R.id.detailsContainer)
                    container.visibility = View.GONE
                    container.isClickable = false
                    container.isFocusable = false
                    requireActivity().supportFragmentManager.popBackStack()
                }
                .setNegativeButton("No", null)
                .show()
        }
    }
}