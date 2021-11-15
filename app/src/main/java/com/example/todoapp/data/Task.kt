package com.example.todoapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

/**
 * Task
 *
 * @author (c) 2021, UVI TECH SAPI De CV, KAVAK
 */
@Entity(tableName = "Tasks")
data class Task(
    @ColumnInfo(name = "title") var title: String = "",
    @ColumnInfo(name = "description") var description: String = "",
    @ColumnInfo(name = "completed") var completed: Boolean = false,
    @PrimaryKey @ColumnInfo(name = "entryid")var id: String = UUID.randomUUID().toString()
)