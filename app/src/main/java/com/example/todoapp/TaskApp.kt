package com.example.todoapp

import android.app.Application
import com.example.todoapp.data.repository.TaskRepository
import com.example.todoapp.di.ServiceLocator

/**
 * TaskApp
 *
 * @author (c) 2021, UVI TECH SAPI De CV, KAVAK
 */
class TaskApp: Application() {

    val taskRepository: TaskRepository
    get() = ServiceLocator.provideTaskRepository(this)

}