package com.example.todoapp.domain

import androidx.room.Update
import com.example.todoapp.data.TaskResult
import com.example.todoapp.data.repository.TaskRepository
import com.example.todoapp.data.room.Task

/**
 * GetTasksUseCase
 *
 * @author (c) 2021, UVI TECH SAPI De CV, KAVAK
 */
class GetTasksUseCase(private val taskRepository: TaskRepository) {

    suspend operator fun invoke(forceUpdate: Boolean): TaskResult<List<TaskDomain>> {
        return taskRepository.getTasks(forceUpdate)
    }

}