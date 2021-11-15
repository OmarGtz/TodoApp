package com.example.todoapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

/**
 * TaskDao
 *
 * @author (c) 2021, UVI TECH SAPI De CV, KAVAK
 */
@Dao
interface TaskDao {

    @Query("select * from Tasks")
    suspend fun getTasks(): List<Task>

    @Insert
    suspend fun saveTask(task: Task)

    @Query("delete from Tasks")
    suspend fun deleteAllTasks()
}