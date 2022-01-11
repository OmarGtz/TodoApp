package com.example.todoapp.data.mapper

import com.example.todoapp.data.room.Task
import com.example.todoapp.domain.model.TaskDomain

/**
 * TaskExt
 *
 * @author (c) 2021, UVI TECH SAPI De CV, KAVAK
 */

fun Task.toDomain(): TaskDomain {
    return TaskDomain(title, description, completed, id)
}