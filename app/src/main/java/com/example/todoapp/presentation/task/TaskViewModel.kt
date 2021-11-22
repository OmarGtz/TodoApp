package com.example.todoapp.presentation.task

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.error.EmptyTasksError
import com.example.todoapp.data.room.Task
import com.example.todoapp.data.repository.TaskRepository
import com.example.todoapp.data.TaskResult
import kotlinx.coroutines.launch

/**
 * TaskViewModel
 *
 * @author (c) 2021, UVI TECH SAPI De CV, KAVAK
 */
class TaskViewModel(private val taskRepository: TaskRepository): ViewModel() {

    private val _items: MutableLiveData<List<Task>> = MutableLiveData()

    val items: LiveData<List<Task>>
    get() = _items

    private val _completedTask: MutableLiveData<Boolean> = MutableLiveData()
    val completedTask: LiveData<Boolean>
        get() = _completedTask



    private val _emptyListError: MutableLiveData<Unit> = MutableLiveData()
    val emptyListError: LiveData<Unit>
        get() = _emptyListError

    init {
        loadTasks(forceUpdate = false)
    }

    fun completedTask(taskId: String, completed: Boolean) {
        viewModelScope.launch {
            taskRepository.completedTask(taskId, completed)
            _completedTask.value = true
        }
    }

    fun loadTasks(forceUpdate: Boolean) {
        viewModelScope.launch {
            val tasksResult = taskRepository.getTasks(forceUpdate)
            when (tasksResult) {
                is  TaskResult.Success -> {
                    _items.value = tasksResult.data!!
                }
                is TaskResult.Error -> {
                    if (tasksResult.exception is EmptyTasksError) {
                        _emptyListError.value = Unit
                    }
                }
                is TaskResult.Loading -> {

                }
            }
        }
    }

}