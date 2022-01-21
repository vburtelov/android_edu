package ru.burtelov.lab6.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import ru.burtelov.lab6.R
import ru.burtelov.lab6.databinding.MainFragmentBinding
import ru.burtelov.lab6.viewmodels.NodeViewModel
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import ru.burtelov.lab6.db.Node

class MainFragment : Fragment() {

    private lateinit var binding: MainFragmentBinding
    private val model: NodeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = MainFragmentBinding.inflate(inflater, container, false)
        setRecyclerAdapter()
        setDialog()
        return binding.root
    }

    private fun setRecyclerAdapter() {
        val recyclerView = binding.recyclerView
        val adapter = NodeListAdapter(getItemColor = ::getItemBackgroundColor)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        adapter.onItemClick = { id ->
            val bundle = bundleOf("id" to id)
            Navigation.findNavController(binding.root).navigate(R.id.navigateToItem, bundle)
        }

        model.nodes.observe(this, { nodes ->
            adapter.submitList(nodes)
        })
    }

    private fun getItemBackgroundColor(node: Node): Int {
        var color = ContextCompat.getColor(context!!, R.color.white)

        val isParent = node.nodes.size > 0
        val isChild = model.nodes.value?.any { it.nodes.contains(node) }

        when {
            isParent and (isChild == true) -> {
                color = ContextCompat.getColor(context!!, R.color.red)
            }
            isParent -> {
                color = ContextCompat.getColor(context!!, R.color.yellow)
            }
            isChild == true -> {
                color = ContextCompat.getColor(context!!, R.color.blue)
            }
        }

        return color
    }

    private fun setDialog() {
        val dialog = AddNodeFragment()
        val supportFragmentManager = activity?.supportFragmentManager
        binding.fab.setOnClickListener {
            if (supportFragmentManager != null) {
                dialog.show(supportFragmentManager, "AddFragment")
            }
        }
    }

}