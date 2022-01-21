package ru.burtelov.lab6

import androidx.annotation.WorkerThread
import ru.burtelov.lab6.db.Dao
import ru.burtelov.lab6.db.Node

// https://developer.android.com/codelabs/android-room-with-a-view-kotlin#8

class Repository(private val dao: Dao) {

    val nodes = dao.getAll()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getNode(id: Long): Node? {
        return dao.get(id)
    }
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(node: Node): Long {
        return dao.insert(node)
    }
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun update(node: Node) {
        return dao.update(node)
    }
}