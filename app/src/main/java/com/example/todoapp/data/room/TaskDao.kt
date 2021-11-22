package com.example.todoapp.data.room

import androidx.room.*

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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveTask(task: Task)

    @Query("delete from Tasks")
    suspend fun deleteAllTasks()

    @Update
    suspend fun updateTask(task: Task): Int

    @Query("UPDATE TASKS SET completed = :completed WHERE entryid = :taskId")
    suspend fun updateCompleted(taskId: String, completed: Boolean)

    @Query("DELETE FROM TASKS WHERE entryid = :taskId")
    suspend fun deleteTask(taskId: String)

    @Query("DELETE FROM TASKS WHERE completed = 1")
    suspend fun deleteCompletedTaskS(): Int


}