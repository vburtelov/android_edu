package ru.burtelov.lab6.ui.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import ru.burtelov.lab6.R
import ru.burtelov.lab6.databinding.ListFragmentBinding
import ru.burtelov.lab6.db.Node
import ru.burtelov.lab6.viewmodels.FilterState
import ru.burtelov.lab6.viewmodels.NodeViewModel

class ListFragment : Fragment() {
    private lateinit var binding: ListFragmentBinding
    private val model: NodeViewModel by activityViewModels()

    private lateinit var currentNode: Node
    private lateinit var filterState: FilterState

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = ListFragmentBinding.inflate(inflater, container, false)
        val currentNodeId = arguments?.getLong("id")
        if (currentNodeId != null) {
            model.getCurrentNode(currentNodeId)
                .observe(this, {
                    currentNode = it
                    setRecyclerAdapter(it.id)
                })
        }
        model.nodes.observe(this, {
            if (currentNodeId != null) {
                model.filterNodesById(currentNodeId)
            }
        })
        model.filterState.observe(this, {
            filterState = it
            if (currentNodeId != null) {
                model.filterNodesById(currentNodeId)
            }
        })
        binding.childBtn.setOnClickListener {
            if (currentNodeId != null) {
                model.changeFilterState(FilterState.Children)
            }
        }
        binding.parentBtn.setOnClickListener {
            if (currentNodeId != null) {
                model.changeFilterState(FilterState.Parents)
            }
        }
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setRecyclerAdapter(id: Long) {
        val recyclerView = binding.nodeRecycler
        val adapter = NodeListAdapter(getItemColor = ::getItemBackgroundColor)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter.onItemClick = { pressedId ->
            if (filterState === FilterState.Children) {
                onAddChild(pressedId)
            } else {
                onAddParent(pressedId)
            }
        }
        model.filterNodesById(id)
        model.filteredNodes.observe(this, { filteredNodes ->
            adapter.submitList(filteredNodes)
        })
    }

    private fun getItemBackgroundColor(node: Node): Int {
        var color = ContextCompat.getColor(context!!, R.color.white)
        if (currentNode.nodes.contains(node) or node.nodes.contains(currentNode)) {
            color = ContextCompat.getColor(context!!, R.color.teal_200)
        }
        return color
    }

    private fun onAddChild(childId: Long) {
        model.getCurrentNode(childId).observe(this, { childNode ->
            if (!currentNode.nodes.contains(childNode)) {
                currentNode.nodes.add(childNode)
                model.update(currentNode)
            }
        })

    }

    private fun onAddParent(parentId: Long) {
        model.getCurrentNode(parentId).observe(this, { parentNode ->
            if (!parentNode.nodes.contains(currentNode)) {
                parentNode.nodes.add(currentNode)
                model.update(parentNode)
            }
        })
    }
}