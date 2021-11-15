package com.example.todoapp.data

/**
 * TaskRepositoryImp
 *
 * @author (c) 2021, UVI TECH SAPI De CV, KAVAK
 */
class TaskRepositoryImp(val taskRemoteDataSource: TaskDataSource,val taskLocalDataSource: TaskDataSource): TaskRepository {

    override suspend fun getTasks(forceUpdate: Boolean): TaskResult<List<Task>> {
       return if (forceUpdate) {
             updateTasksFromRemoteDataSource()
        } else {
            taskLocalDataSource.getTasks()
        }
    }

    private suspend fun updateTasksFromRemoteDataSource(): TaskResult<List<Task>> {
        val remoteTask = taskRemoteDataSource.getTasks()
        when (remoteTask) {
            is TaskResult.Success -> {
                val data = remoteTask.data
                for (task in data) {
                    taskLocalDataSource.saveTask(task)
                }
                return TaskResult.Success(data)
            }
            is TaskResult.Error -> {
                return TaskResult.Error(remoteTask.exception)
            }

            is TaskResult.Loading -> {
                return remoteTask
            }
        }
    }
}