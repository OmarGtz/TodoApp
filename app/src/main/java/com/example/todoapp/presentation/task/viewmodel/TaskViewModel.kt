package com.example.todoapp.presentation.task.viewmodel

import androidx.lifecycle.*
import com.example.todoapp.core.TaskResult
import com.example.todoapp.data.room.Task
import com.example.todoapp.domain.mapper.toPresentation
import com.example.todoapp.domain.model.TaskDomain
import com.example.todoapp.domain.usecase.CompleteTaskParams
import com.example.todoapp.domain.usecase.CompleteTaskUseCase
import com.example.todoapp.domain.usecase.GetTasksUseCase
import com.example.todoapp.presentation.task.model.TasksView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * TaskViewModel
 *
 * @author (c) 2021, UVI TECH SAPI De CV, KAVAK
 */
@HiltViewModel
class TaskViewModel @Inject constructor(
    private val getTasksUseCase: GetTasksUseCase,
    private val completedTaskUseCase: CompleteTaskUseCase
) : ViewModel() {

    private val _forceUpdate = MutableLiveData(false)

    private val _items: MutableLiveData<List<TaskDomain>> = MutableLiveData()

    val items: LiveData<TasksView>
        get() = _items.map { it.toPresentation() }

    private val _completedTask: MutableLiveData<Boolean> = MutableLiveData()

    val completedTask: LiveData<Boolean>
        get() = _completedTask

    private val _emptyListError: MutableLiveData<Unit> = MutableLiveData()
    val emptyListError: LiveData<Unit>
        get() = _emptyListError

    init {
        loadTasks(forceUpdate = false)
    }

    fun handleResult(taskResult: TaskResult<List<Task>>): LiveData<List<Task>> {
        val result: MutableLiveData<List<Task>> = MutableLiveData()

        if (taskResult is TaskResult.Success) {
            result.value = taskResult.data
        } else if (taskResult is TaskResult.Error) {
            _emptyListError.value = Unit
            result.value = emptyList()
        }
        return result
    }

    fun completedTask(taskId: String, completed: Boolean) {
        viewModelScope.launch {
            completedTaskUseCase(CompleteTaskParams(taskId, completed))
            _completedTask.value = true
        }
    }

    fun loadTasks(forceUpdate: Boolean) {
        viewModelScope.launch {
            val tasks = getTasksUseCase(forceUpdate)
            if (tasks is TaskResult.Success) {
                _items.value = tasks.data!!
            }
        }
    }

}