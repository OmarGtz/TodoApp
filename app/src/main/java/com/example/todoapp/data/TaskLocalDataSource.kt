package com.example.todoapp.data

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

/**
 * TaskLocalDataSource
 *
 * @author (c) 2021, UVI TECH SAPI De CV, KAVAK
 */
class TaskLocalDataSource(private val taskDao: TaskDao, val ioDispatcher: CoroutineDispatcher = Dispatchers.IO): TaskDataSource {

    override suspend fun getTasks(): TaskResult<List<Task>> =
        withContext(ioDispatcher) {
            return@withContext try {
                val tasks = taskDao.getTasks()
                TaskResult.Success(tasks)
            } catch (e: Exception) {
                TaskResult.Error(e)
            }
        }

    override suspend fun saveTask(task: Task): TaskResult<Task> = withContext(ioDispatcher) {
        taskDao.saveTask(task)
        return@withContext TaskResult.Success(task)
    }

    override suspend fun deleteTasks() = withContext(ioDispatcher) {
        taskDao.deleteAllTasks()
    }
}