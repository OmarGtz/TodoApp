package com.example.todoapp.di

import androidx.transition.Visibility
import com.example.todoapp.data.datasource.TaskDataSource
import com.example.todoapp.data.datasource.local.TaskLocalDataSource
import com.example.todoapp.data.repository.TaskRepository
import com.example.todoapp.data.repository.TaskRepositoryImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * TaskRepositoryModule
 *
 * @author (c) 2021, UVI TECH SAPI De CV, KAVAK
 */
@InstallIn(SingletonComponent::class)
@Module
class TaskRepositoryModule {

    @Provides
    @Singleton
    fun providesTasksRepository(
        @ApplicationModule.LocalTaskDataSource taskLocalDataSource: TaskDataSource,
        @ApplicationModule.RemoteTaskDataSource taskRemoteDataSource: TaskDataSource
    ): TaskRepository {
        return TaskRepositoryImp(
            taskLocalDataSource = taskLocalDataSource,
            taskRemoteDataSource = taskRemoteDataSource
        )
    }

}