package com.example.todoapp.data.datasource.remote

import com.example.todoapp.data.room.Task
import com.example.todoapp.data.datasource.TaskDataSource
import com.example.todoapp.data.TaskResult
import kotlinx.coroutines.delay

/**
 * TaskRemoteDataSource
 *
 * @author (c) 2021, UVI TECH SAPI De CV, KAVAK
 */
object TaskRemoteDataSource: TaskDataSource {

    private var TASK_SERVICE_DATA: LinkedHashMap<String, Task> = java.util.LinkedHashMap()

    init {
        val tasks: List<Task> = listOf(
            Task("Crear nuevo feature", "Crear nuevo feature de TodoApp", false),
            Task("Mandar MR", "Enviar MR para review", false),
            Task("Resolver bugs", "Enviar bugs encontrados", false)
        )
        for (task in tasks) {
            TASK_SERVICE_DATA[task.id] = task
        }
    }

    override suspend fun getTasks(): TaskResult<List<Task>> {
        delay(2000)
        return TaskResult.Success(TASK_SERVICE_DATA.values.toList())
    }

    override suspend fun saveTask(task: Task) {
        TASK_SERVICE_DATA[task.id] = task
    }

    override suspend fun deleteTasks() {
        TODO("Not yet implemented")
    }
}