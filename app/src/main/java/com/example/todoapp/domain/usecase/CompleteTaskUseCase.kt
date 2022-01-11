package com.example.todoapp.domain.usecase

import com.example.todoapp.core.CoroutineUseCase
import com.example.todoapp.data.repository.TaskRepository
import com.example.todoapp.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

/**
 * CompleteTaskUseCase
 *
 * @author (c) 2021, UVI TECH SAPI De CV, KAVAK
 */
class CompleteTaskUseCase @Inject constructor(
    private val taskRepository: TaskRepository,
    @IoDispatcher coroutineDispatcher: CoroutineDispatcher
): CoroutineUseCase<CompleteTaskParams, Unit>(coroutineDispatcher) {
    override suspend fun execute(parameters: CompleteTaskParams) {
        taskRepository.completedTask(tasId = parameters.taskId, completed = parameters.status)
    }
}

data class CompleteTaskParams(
    val taskId: String,
    val status: Boolean
)