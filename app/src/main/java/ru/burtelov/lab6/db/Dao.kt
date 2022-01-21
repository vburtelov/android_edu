package ru.burtelov.lab6.db

import androidx.room.*
import androidx.room.Dao
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {
    @Insert
    fun insert(node: Node): Long

    @Query("SELECT * from Node WHERE id = :key")
    fun get(key: Long): Node?

    @Update
    fun update(node: Node)

    @Query("SELECT * FROM Node")
    fun getAll(): Flow<List<Node>>
}