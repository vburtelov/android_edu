package ru.burtelov.lab6.db

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "node")
data class Node(
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id") val id: Long,
    @ColumnInfo(name = "value") val value: String,
    @ColumnInfo(name = "nodes") val nodes: MutableList<Node>
)