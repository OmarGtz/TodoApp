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
        return TaskResult.Success(TASK_SERVICE_DATA.values.toList())
    }

    override suspend fun saveTask(task: Task) {
        TASK_SERVICE_DATA[task.id] = task
    }

    override suspend fun deleteTasks() {
        TASK_SERVICE_DATA.clear()
    }

    override suspend fun getTask(id: String): TaskResult<Task> {
        return TaskResult.Success(TASK_SERVICE_DATA[id] ?: Task())
    }

    override suspend fun updateTask(task: Task): TaskResult<Boolean> {
        TASK_SERVICE_DATA[task.id] = task
        return TaskResult.Success(true)
    }

    override suspend fun completedTask(taskId: String, completed: Boolean) {
        TASK_SERVICE_DATA[taskId]?.completed = completed
    }

    override suspend fun deleteCompleteTasks(): TaskResult<Int> {
        var deletedItems = 0
        for (task in TASK_SERVICE_DATA) {
            if (task.value.completed) {
                TASK_SERVICE_DATA.remove(task.key)
                deletedItems++
            }
        }
        return TaskResult.Success(deletedItems)
    }

    override suspend fun deleteTask(taskId: String) {
        TASK_SERVICE_DATA.remove(taskId)
    }

}