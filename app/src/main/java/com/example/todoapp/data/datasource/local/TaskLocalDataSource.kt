package com.example.todoapp.data.datasource.local

import com.example.todoapp.data.*
import com.example.todoapp.data.datasource.TaskDataSource
import com.example.todoapp.data.error.EmptyTasksError
import com.example.todoapp.data.error.NotDataFoundError
import com.example.todoapp.data.room.Task
import com.example.todoapp.data.room.TaskDao
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

/**
 * TaskLocalDataSource
 *
 * @author (c) 2021, UVI TECH SAPI De CV, KAVAK
 */
class TaskLocalDataSource(private val taskDao: TaskDao, val ioDispatcher: CoroutineDispatcher = Dispatchers.IO):
    TaskDataSource {

    override suspend fun getTasks(): TaskResult<List<Task>> =
        withContext(ioDispatcher) {
            return@withContext try {
                val tasks = taskDao.getTasks()
                if (tasks.isEmpty()) {
                    throw EmptyTasksError()
                }
                TaskResult.Success(tasks)
            } catch (e: Exception) {
                TaskResult.Error(e)
            }
        }

    override suspend fun saveTask(task: Task): Unit = withContext(ioDispatcher) {
        taskDao.saveTask(task)
    }

    override suspend fun deleteTasks() = withContext(ioDispatcher) {
        taskDao.deleteAllTasks()
    }

    override suspend fun getTask(id: String): TaskResult<Task> = withContext(ioDispatcher) {
        return@withContext try {
            val task = taskDao.getTask(id)
            if (task != null) {
                TaskResult.Success(task)
            } else {
                TaskResult.Error(NotDataFoundError())
            }
        } catch (e: Exception) {
            TaskResult.Error(e)
        }
    }

    override suspend fun updateTask(task: Task): TaskResult<Boolean> = withContext(ioDispatcher) {
        return@withContext try {
            val updated = taskDao.updateTask(task)
            if (updated >= 1) {
                TaskResult.Success(true)
            } else {
                TaskResult.Success(false)
            }
        } catch (e: Exception) {
            TaskResult.Error(e)
        }
    }
    override suspend fun completedTask(taskId: String, completed: Boolean) = withContext(ioDispatcher) {
        taskDao.updateCompleted(taskId, completed)
    }

    override suspend fun deleteCompleteTasks(): TaskResult<Int> = withContext(ioDispatcher) {
        val taskUpdated = taskDao.deleteCompletedTaskS()
        return@withContext TaskResult.Success(taskUpdated)
    }

    override suspend fun deleteTask(taskId: String) = withContext(ioDispatcher) {
        taskDao.deleteTask(taskId)
    }
}