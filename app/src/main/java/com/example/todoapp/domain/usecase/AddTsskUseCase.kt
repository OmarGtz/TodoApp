package com.example.todoapp.domain.usecase

import com.example.todoapp.core.CoroutineUseCase
import com.example.todoapp.data.repository.TaskRepository
import com.example.todoapp.data.room.Task
import com.example.todoapp.di.IoDispatcher
import com.example.todoapp.domain.error.EmptyDescriptionTaskError
import com.example.todoapp.domain.error.EmptyTitleTaskError
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

/**
 * AddTsskUseCase
 *
 * @author (c) 2021, UVI TECH SAPI De CV, KAVAK
 */
class AddTaskUseCase @Inject constructor(val taskRepository: TaskRepository, @IoDispatcher dispatcher: CoroutineDispatcher): CoroutineUseCase<AddTaskParams, Boolean>(dispatcher) {
    override suspend fun execute(parameters: AddTaskParams): Boolean {
        if (parameters.title.isEmpty()) throw EmptyTitleTaskError()
        if (parameters.description.isEmpty()) throw EmptyDescriptionTaskError()
        taskRepository.saveTask(Task(parameters.title, parameters.description))
        return true
    }
}

data class AddTaskParams(
    val title: String,
    val description: String,
    val completed: Boolean = false
)