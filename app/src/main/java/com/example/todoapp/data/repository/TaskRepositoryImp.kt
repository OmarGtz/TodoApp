package com.example.todoapp.data.repository

import androidx.lifecycle.LiveData
import com.example.todoapp.data.room.Task
import com.example.todoapp.data.datasource.TaskDataSource
import com.example.todoapp.core.TaskResult
import com.example.todoapp.data.mapper.toDomain
import com.example.todoapp.domain.model.TaskDomain
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

/**
 * TaskRepositoryImp
 *
 * @author (c) 2021, UVI TECH SAPI De CV, KAVAK
 */
class TaskRepositoryImp(private val taskRemoteDataSource: TaskDataSource, private val taskLocalDataSource: TaskDataSource):
    TaskRepository {

    override suspend fun getTasks(forceUpdate: Boolean): List<TaskDomain> {
        if (forceUpdate) {
                updateTasksFromRemoteDataSource()
        }
       return taskLocalDataSource.getTasks().map { it.toDomain() }
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

    override fun observeTasks(): LiveData<TaskResult<List<Task>>> {
           return taskLocalDataSource.observeTasks()
    }

    private suspend fun updateTasksFromRemoteDataSource() {
        val remoteTasks = taskRemoteDataSource.getTasks()
            // Real apps might want to do a proper sync, deleting, modifying or adding each task.
            taskLocalDataSource.deleteTasks()
            for (task in remoteTasks) {
                taskLocalDataSource.saveTask(task)
            }
    }
}