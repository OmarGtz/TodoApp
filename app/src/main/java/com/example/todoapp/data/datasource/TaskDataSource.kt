package com.example.todoapp.data.datasource

import androidx.lifecycle.LiveData
import com.example.todoapp.data.room.Task
import com.example.todoapp.core.TaskResult

/**
 * TaskDataSource
 *
 * @author (c) 2021, UVI TECH SAPI De CV, KAVAK
 */
interface TaskDataSource {
    suspend fun getTasks(): List<Task>
    suspend fun saveTask(task: Task)
    suspend fun deleteTasks()
    suspend fun getTask(id: String): TaskResult<Task>
    suspend fun updateTask(task: Task): TaskResult<Boolean>
    suspend fun completedTask(taskId: String, completed: Boolean)
    suspend fun deleteCompleteTasks(): TaskResult<Int>
    suspend fun deleteTask(taskId: String)
    fun observeTasks(): LiveData<TaskResult<List<Task>>>
}