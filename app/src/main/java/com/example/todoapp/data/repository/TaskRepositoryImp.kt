package com.example.todoapp.data.repository

import com.example.todoapp.data.room.Task
import com.example.todoapp.data.datasource.TaskDataSource
import com.example.todoapp.data.TaskResult
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

/**
 * TaskRepositoryImp
 *
 * @author (c) 2021, UVI TECH SAPI De CV, KAVAK
 */
class TaskRepositoryImp(private val taskRemoteDataSource: TaskDataSource, private val taskLocalDataSource: TaskDataSource):
    TaskRepository {

    override suspend fun getTasks(forceUpdate: Boolean): TaskResult<List<Task>> {
        if (forceUpdate) {
            try {
                updateTasksFromRemoteDataSource()
            } catch (ex: Exception) {
                return TaskResult.Error(ex)
            }
        }
        return taskLocalDataSource.getTasks()
    }

    override suspend fun saveTask(task: Task) {
        coroutineScope {
            launch { taskRemoteDataSource.saveTask(task) }
            launch { taskLocalDataSource.saveTask(task) }
        }
    }

    override suspend fun getTask(id: String): TaskResult<Task> {
        return taskLocalDataSource.getTask(id)
    }

    override suspend fun completedTask(tasId: String, completed: Boolean) {
        coroutineScope {
            launch { taskRemoteDataSource.completedTask(tasId, completed) }
            launch { taskLocalDataSource.completedTask(tasId, completed) }
        }
    }

    override suspend fun clearCompleteTask() {
        coroutineScope {
            launch { taskRemoteDataSource.deleteCompleteTasks() }
            launch { taskLocalDataSource.deleteCompleteTasks() }
        }
    }

    override suspend fun deleteTask(tasId: String) {
        coroutineScope {
            launch { taskRemoteDataSource.deleteTask(tasId) }
            launch { taskLocalDataSource.deleteTask(tasId) }
        }
    }

    private suspend fun updateTasksFromRemoteDataSource() {
        val remoteTasks = taskRemoteDataSource.getTasks()
        if (remoteTasks is TaskResult.Success) {
            // Real apps might want to do a proper sync, deleting, modifying or adding each task.
            taskLocalDataSource.deleteTasks()
            for (task in remoteTasks.data) {
                taskLocalDataSource.saveTask(task)
            }
        } else if (remoteTasks is TaskResult.Error) {
            throw remoteTasks.exception
        }
    }
}