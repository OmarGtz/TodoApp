package com.example.todoapp.data

import kotlinx.coroutines.delay

/**
 * TaskRemoteDataSource
 *
 * @author (c) 2021, UVI TECH SAPI De CV, KAVAK
 */
class TaskRemoteDataSource(): TaskDataSource {

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

    override suspend fun saveTask(task: Task): TaskResult<Task> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTasks() {
        TODO("Not yet implemented")
    }
}