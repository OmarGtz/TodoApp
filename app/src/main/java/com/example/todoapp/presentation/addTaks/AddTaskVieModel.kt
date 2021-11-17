package com.example.todoapp.presentation.addTaks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.repository.TaskRepository
import com.example.todoapp.data.room.Task
import kotlinx.coroutines.launch

/**
 * AddTaskVieModel
 *
 * @author (c) 2021, UVI TECH SAPI De CV, KAVAK
 */
class AddTaskVieModel(private val taskRepository: TaskRepository): ViewModel() {

    private val _addTaskSuccess: MutableLiveData<Unit> = MutableLiveData()
    val addTaskSuccess: LiveData<Unit>
    get() = _addTaskSuccess

    fun addTask(title: String, desc: String) {
        viewModelScope.launch {
            val task = Task(title = title, description = desc)
            taskRepository.saveTask(task)
            _addTaskSuccess.value = Unit
        }
    }
}