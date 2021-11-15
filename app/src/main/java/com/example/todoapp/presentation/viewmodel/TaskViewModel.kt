package com.example.todoapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.Task
import com.example.todoapp.data.TaskRepository
import com.example.todoapp.data.TaskResult
import kotlinx.coroutines.launch

/**
 * TaskViewModel
 *
 * @author (c) 2021, UVI TECH SAPI De CV, KAVAK
 */
class TaskViewModel(val taskRepository: TaskRepository): ViewModel() {

    private val _items: MutableLiveData<List<Task>> = MutableLiveData()

    val items: LiveData<List<Task>>
    get() = _items

    private val _error: MutableLiveData<Throwable> = MutableLiveData()
    val error: LiveData<Throwable>
        get() = _error


    private fun loadTasks(forceUpdate: Boolean) {
        viewModelScope.launch {
            val tasksResult = taskRepository.getTasks(forceUpdate)
            when (tasksResult) {
                is  TaskResult.Success -> {
                    _items.value = tasksResult.data!!
                }
                is TaskResult.Error -> {
                    _error.value = tasksResult.exception
                }
                is TaskResult.Loading -> {

                }
            }
        }
    }

}