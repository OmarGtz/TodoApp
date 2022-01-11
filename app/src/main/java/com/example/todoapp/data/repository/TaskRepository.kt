package com.example.todoapp.data.repository

import androidx.lifecycle.LiveData
import com.example.todoapp.data.room.Task
import com.example.todoapp.core.TaskResult
import com.example.todoapp.domain.model.TaskDomain

/**
 * TaskRepository
 *
 * @author (c) 2021, UVI TECH SAPI De CV, KAVAK
 */
interface TaskRepository {
    suspend fun getTasks(forceUpdate: Boolean): List<TaskDomain>
    suspend fun saveTask(task: Task)
    suspend fun getTask(id: String): TaskResult<Task>
    suspend fun completedTask(tasId: String, completed: Boolean)
    suspend fun clearCompleteTask()

    suspend fun deleteTask(tasId: String)
    fun observeTasks(): LiveData<TaskResult<List<Task>>>

}