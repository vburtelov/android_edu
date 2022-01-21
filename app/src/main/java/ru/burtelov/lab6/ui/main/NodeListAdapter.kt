package ru.burtelov.lab6.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.burtelov.lab6.db.Node
import ru.burtelov.lab6.databinding.NodeListBinding

class NodeListAdapter(
    private val getItemColor: (node: Node) -> Int,
) : ListAdapter<Node, NodeListAdapter.NodeViewHolder>(NODE_COMPARATOR) {

    var onItemClick: ((id: Long) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): NodeViewHolder {
        val binding = NodeListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NodeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NodeViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
    }

    inner class NodeViewHolder internal constructor(
        private val binding: NodeListBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(node: Node) = binding.run {
            textView.text = node.value
            list.setOnClickListener {
                onItemClick?.invoke(node.id)
            }
            val backgroundColor = getItemColor(node)
            textView.setBackgroundColor(backgroundColor)
        }
    }

    companion object {
        private val NODE_COMPARATOR = object : DiffUtil.ItemCallback<Node>() {
            override fun areItemsTheSame(oldItem: Node, newItem: Node): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Node, newItem: Node): Boolean {
                return oldItem.value == newItem.value
            }
        }
    }
}