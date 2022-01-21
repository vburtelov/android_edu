package ru.burtelov.lab6.ui.main

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import ru.burtelov.lab6.databinding.AddNodeFragmentBinding
import ru.burtelov.lab6.viewmodels.NodeViewModel
import androidx.fragment.app.activityViewModels
import ru.burtelov.lab6.db.Node
import java.lang.IllegalStateException

class AddNodeFragment : DialogFragment() {

    private lateinit var binding: AddNodeFragmentBinding
    private val model: NodeViewModel by activityViewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = AddNodeFragmentBinding.inflate(LayoutInflater.from(context))
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setView(binding.root)
            builder.setPositiveButton("Add") { _, _ ->
                val text = binding.text.text.toString()
                if (text.isNotEmpty()) {
                    onAdd(text)
                }
            }
            builder.setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }
            builder.create()
        } ?: throw IllegalStateException("Activity is null")
    }

    private fun onAdd(text: String) {
        val node = Node(0, text, mutableListOf<Node>())
        model.insert(node)
    }
}