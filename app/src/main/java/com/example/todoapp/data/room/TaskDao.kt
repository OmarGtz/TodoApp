package com.example.todoapp.data.room

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

    @Query("select * from tasks where entryid = :taskId")
    suspend fun getTask(taskId: String): Task

    @Insert
    suspend fun saveTask(task: Task)

    @Query("delete from Tasks")
    suspend fun deleteAllTasks()


}