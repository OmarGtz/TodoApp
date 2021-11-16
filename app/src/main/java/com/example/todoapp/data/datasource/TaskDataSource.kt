package com.example.todoapp.data.datasource

import com.example.todoapp.data.room.Task
import com.example.todoapp.data.TaskResult

/**
 * TaskDataSource
 *
 * @author (c) 2021, UVI TECH SAPI De CV, KAVAK
 */
interface TaskDataSource {
    suspend fun getTasks(): TaskResult<List<Task>>
    suspend fun saveTask(task: Task)
    suspend fun deleteTasks()
}