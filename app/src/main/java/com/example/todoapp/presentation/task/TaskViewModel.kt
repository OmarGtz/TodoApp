package com.example.todoapp.presentation.task

import androidx.lifecycle.*
import com.example.todoapp.data.error.EmptyTasksError
import com.example.todoapp.data.room.Task
import com.example.todoapp.data.repository.TaskRepository
import com.example.todoapp.data.TaskResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * TaskViewModel
 *
 * @author (c) 2021, UVI TECH SAPI De CV, KAVAK
 */
@HiltViewModel
class TaskViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
    private val savedStateHandle: SavedStateHandle
    ): ViewModel() {

    private val _forceUpdate = MutableLiveData(false)

    private val _items: LiveData<List<Task>> = _forceUpdate.switchMap { forceUpdate ->
        if (forceUpdate) {
            viewModelScope.launch {
                taskRepository.getTasks(true)
            }
        }
        taskRepository.observeTasks().distinctUntilChanged().switchMap {
            handleResult(it)
        }
    }

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

    fun handleResult(taskResult: TaskResult<List<Task>>): LiveData<List<Task>> {
        val result: MutableLiveData<List<Task>> = MutableLiveData()

        if (taskResult is TaskResult.Success)  {
            result.value = taskResult.data
        } else if (taskResult is TaskResult.Error) {
            _emptyListError.value = Unit
            result.value = emptyList()
        }
        return result
    }

    fun completedTask(taskId: String, completed: Boolean) {
        viewModelScope.launch {
            taskRepository.completedTask(taskId, completed)
            _completedTask.value = true
        }
    }

    fun loadTasks(forceUpdate: Boolean) {
        _forceUpdate.value = forceUpdate
    }

}