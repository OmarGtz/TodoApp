package com.example.todoapp.data

/**
 * TaskDataSource
 *
 * @author (c) 2021, UVI TECH SAPI De CV, KAVAK
 */
interface TaskDataSource {
    suspend fun getTasks(): TaskResult<List<Task>>
    suspend fun saveTask(task: Task): TaskResult<Task>
    suspend fun deleteTasks()
}