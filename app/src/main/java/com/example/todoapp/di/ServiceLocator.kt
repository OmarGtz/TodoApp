package com.example.todoapp.di

import android.content.Context
import androidx.room.Room
import com.example.todoapp.data.datasource.TaskDataSource
import com.example.todoapp.data.datasource.local.TaskLocalDataSource
import com.example.todoapp.data.datasource.remote.TaskRemoteDataSource
import com.example.todoapp.data.repository.TaskRepository
import com.example.todoapp.data.repository.TaskRepositoryImp
import com.example.todoapp.data.room.TodoDatabase

/**
 * ServiceLocator
 *
 * @author (c) 2021, UVI TECH SAPI De CV, KAVAK
 */
object ServiceLocator {

    private var database: TodoDatabase? = null

    private var taskRepository: TaskRepository? = null

    fun provideTaskRepository(context: Context): TaskRepository {
        return taskRepository ?: createTaskRepository(context)
    }

    private fun createTaskRepository(context: Context) : TaskRepository {
        val repo = TaskRepositoryImp(TaskRemoteDataSource, createLocalDataSource(context = context))
        taskRepository = repo
        return repo
    }

    private fun createLocalDataSource(context: Context): TaskDataSource {
        val db = database ?: createDataBase(context)
        return TaskLocalDataSource(db.taskDao())
    }

    private fun createDataBase(
        context: Context,
        inMemory: Boolean = false
    ): TodoDatabase {
        val result = if (inMemory) {
            Room.inMemoryDatabaseBuilder(context.applicationContext, TodoDatabase::class.java)
                .allowMainThreadQueries()
                .build()
        } else {
            Room.databaseBuilder(
                context.applicationContext,
                TodoDatabase::class.java, "TasksDb.db"
            ).build()
        }
        database = result
        return result
    }

}