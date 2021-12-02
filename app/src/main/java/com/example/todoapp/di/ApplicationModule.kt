package com.example.todoapp.di

import android.content.Context
import androidx.room.Room
import com.example.todoapp.data.datasource.TaskDataSource
import com.example.todoapp.data.datasource.local.TaskLocalDataSource
import com.example.todoapp.data.datasource.remote.TaskRemoteDataSource
import com.example.todoapp.data.room.TodoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier
import javax.inject.Singleton

/**
 * ApplicatonModule
 *
 * @author (c) 2021, UVI TECH SAPI De CV, KAVAK
 */
@InstallIn(SingletonComponent::class)
@Module
class ApplicationModule {

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class RemoteTaskDataSource

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class LocalTaskDataSource

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): TodoDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            TodoDatabase::class.java,
            "Tasks.db"
        ).build()
    }

    @Provides
    @Singleton
    @RemoteTaskDataSource
    fun providesRemoteDataSource(): TaskDataSource {
        return TaskRemoteDataSource
    }

    @Provides
    @Singleton
    fun provideIoDispatcher() = Dispatchers.IO

    @Provides
    @Singleton
    @LocalTaskDataSource
    fun provideLocalDataSource(
        dataBase: TodoDatabase,
        ioDispatcher: CoroutineDispatcher
    ): TaskDataSource {
        return TaskLocalDataSource(
            dataBase.taskDao(),
            ioDispatcher
        )
    }


}