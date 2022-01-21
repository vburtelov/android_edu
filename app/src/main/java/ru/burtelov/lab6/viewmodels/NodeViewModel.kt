package ru.burtelov.lab6.viewmodels

import androidx.lifecycle.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.burtelov.lab6.Repository
import ru.burtelov.lab6.db.Node

enum class FilterState { Children, Parents }

class NodeViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NodeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NodeViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class NodeViewModel(private val repository: Repository) : ViewModel() {
    private val defDispatcher: CoroutineDispatcher = Dispatchers.IO
    val nodes = repository.nodes.asLiveData()
    var filterState: MutableLiveData<FilterState> = MutableLiveData(FilterState.Children)
    val filteredNodes: MutableLiveData<List<Node>> = MutableLiveData(emptyList())

    fun insert(node: Node) {
        viewModelScope.launch(defDispatcher) {
            repository.insert(node)
        }
    }

    fun update(node: Node) {
        viewModelScope.launch(defDispatcher) {
            repository.update(node)
        }
    }

    fun changeFilterState(newState: FilterState) {
        viewModelScope.launch(defDispatcher) {
            filterState.postValue(newState)
        }
    }

    fun getCurrentNode(id: Long): LiveData<Node> {
        val currentNode = MutableLiveData<Node>()
        viewModelScope.launch(defDispatcher) {
            val node = repository.getNode(id)
            currentNode.postValue(node!!)
        }
        return currentNode
    }

    fun filterNodesById(id: Long) {
        viewModelScope.launch(defDispatcher) {
            if (filterState.value === FilterState.Children) {
                filterByChildren(id)
            } else if (filterState.value === FilterState.Parents) {
                filterByParents(id)
            }
        }
    }

    private fun filterByChildren(id: Long) {
        val currentNode = nodes.value?.find { it.id == id }
        val filteredNodes = nodes.value?.filter { !it.nodes.contains(currentNode) && id != it.id }
        this.filteredNodes.postValue(filteredNodes!!)
    }

    private fun filterByParents(id: Long) {
        val currentNode = nodes.value?.find { it.id == id }
        if (currentNode != null) {
            val filteredNodes =
                nodes.value?.filter { !currentNode.nodes.contains(it) && id != it.id }
            this.filteredNodes.postValue(filteredNodes!!)
        }
    }
}