package com.example.todoapp.data

/**
 * TaskRepository
 *
 * @author (c) 2021, UVI TECH SAPI De CV, KAVAK
 */
interface TaskRepository {
    suspend fun getTasks(forceUpdate: Boolean): TaskResult<List<Task>>
}