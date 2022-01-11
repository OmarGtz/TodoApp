package com.example.todoapp.presentation.taskDetail

import androidx.lifecycle.*
import com.example.todoapp.R
import com.example.todoapp.core.TaskResult
import com.example.todoapp.domain.error.NotDataFoundError
import com.example.todoapp.data.repository.TaskRepository
import com.example.todoapp.data.room.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * TaskDetailViewModel
 *
 * @author (c) 2021, UVI TECH SAPI De CV, KAVAK
 */
@HiltViewModel
class TaskDetailViewModel @Inject constructor(private val taskRepository: TaskRepository): ViewModel() {

    private val _taskDetail: MutableLiveData<Task> = MutableLiveData()
    val taskDetail: LiveData<Task>
    get() = _taskDetail

    private val _taskError: MutableLiveData<Int> = MutableLiveData()
    val taskError: LiveData<Int>
        get() = _taskError

    private val _notDataFound: MutableLiveData<Unit> = MutableLiveData()


    val isDataAvailable: LiveData<Boolean> = _taskDetail.map { task -> task != null }

    private val _dataLoading: MutableLiveData<Boolean> = MutableLiveData()
    val dataLoading: LiveData<Boolean>
        get() = _dataLoading

    fun onStart(taskId: String) {
        if (taskId.isNotEmpty()) {
            getTaskDetail(taskId)
        } else {
            _taskError.value = R.string.task_error
        }
    }

    private fun getTaskDetail(id: String) {
        viewModelScope.launch {
            _dataLoading.value = true
            when (val result = taskRepository.getTask(id)) {
                is TaskResult.Success -> {
                    _dataLoading.value = false
                    _taskDetail.value = result.data!!
                }
                is TaskResult.Error -> {
                    _dataLoading.value = false
                    if (result.exception is NotDataFoundError) {
                        _taskError.value = R.string.not_data_found_error
                    } else {
                        _taskError.value = R.string.not_data_found_error
                    }
                }
            }
        }
    }
}