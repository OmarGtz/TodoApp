package com.example.todoapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todoapp.data.repository.TaskRepository
import com.example.todoapp.presentation.addTaks.AddTaskVieModel
import com.example.todoapp.presentation.task.TaskViewModel
import com.example.todoapp.presentation.taskDetail.TaskDetailViewModel

/**
 * ViewModelFactory
 *
 * @author (c) 2021, UVI TECH SAPI De CV, KAVAK
 */
class ViewModelFactory(private val taskRepository: TaskRepository): ViewModelProvider.Factory {
    /**
     * Creates a new instance of the given `Class`.
     *
     * @param modelClass a `Class` whose instance is requested
     * @return a newly created ViewModel
     */
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(TaskViewModel::class.java) -> TaskViewModel(taskRepository)
            modelClass.isAssignableFrom(AddTaskVieModel::class.java) -> AddTaskVieModel(taskRepository)
            modelClass.isAssignableFrom(TaskDetailViewModel::class.java) -> TaskDetailViewModel(taskRepository)
            else -> throw IllegalArgumentException("Unknown viewmodel")
        } as T
    }
}