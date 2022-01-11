package com.example.todoapp.domain.usecase

import com.example.todoapp.core.CoroutineUseCase
import com.example.todoapp.data.repository.TaskRepository
import com.example.todoapp.domain.error.EmptyTasksError
import com.example.todoapp.domain.model.TaskDomain
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

/**
 * GetTasksUseCase
 *
 * @author (c) 2021, UVI TECH SAPI De CV, KAVAK
 */
class GetTasksUseCase @Inject constructor(private val taskRepository: TaskRepository, coroutineDispatcher: CoroutineDispatcher): CoroutineUseCase<Boolean, List<TaskDomain>>(coroutineDispatcher) {
    override suspend fun execute(parameters: Boolean): List<TaskDomain> {
        val tasks = taskRepository.getTasks(parameters)
        if (tasks.isEmpty()) throw EmptyTasksError()
        return tasks
    }
}