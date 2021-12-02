package com.example.todoapp.presentation.addTaks

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.TaskResult
import com.example.todoapp.data.repository.TaskRepository
import com.example.todoapp.data.room.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * AddTaskVieModel
 *
 * @author (c) 2021, UVI TECH SAPI De CV, KAVAK
 */
@HiltViewModel
class AddTaskVieModel @Inject constructor(private val taskRepository: TaskRepository): ViewModel() {

    private val _addTaskSuccess: MutableLiveData<Unit> = MutableLiveData()
    val addTaskSuccess: LiveData<Unit>
    get() = _addTaskSuccess

    private val _updateTaskSuccess: MutableLiveData<Unit> = MutableLiveData()
    val updateTaskSuccess: LiveData<Unit>
        get() = _updateTaskSuccess

    private val _onTaskLoaded: MutableLiveData<Task> = MutableLiveData()
    val onTaskLoaded: LiveData<Task>
    get() = _onTaskLoaded

    private var currentTask: Task? = null

    fun onStart(taskId: String) {

    }

    private fun loadTask(taskId: String) {
        viewModelScope.launch {
            val taskResult = taskRepository.getTask(taskId)
            if (taskResult is TaskResult.Success) {
                _onTaskLoaded.value = taskResult.data!!
                currentTask = taskResult.data
            } else {
                    Log.e("Task not loaded", "Task not loaded")
            }
        }
    }

    fun updateTask(title: String, desc: String) {
        viewModelScope.launch {
            val task = Task(title, desc, currentTask?.completed ?: false, currentTask?.id ?: "")
            taskRepository.saveTask(task)
            _updateTaskSuccess.value = Unit
        }
    }

    fun saveTask(title: String, desc: String) {
        if (currentTask != null) {
            updateTask(title, desc)
        } else {
            addTask(title, desc)
        }
    }

    fun addTask(title: String, desc: String) {
        viewModelScope.launch {
            val task = Task(title = title, description = desc)
            taskRepository.saveTask(task)
            _addTaskSuccess.value = Unit
        }
    }
}