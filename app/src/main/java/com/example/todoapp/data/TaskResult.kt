package com.example.todoapp.data

import java.lang.Exception

/**
 * TaskResult
 *
 * @author (c) 2021, UVI TECH SAPI De CV, KAVAK
 */
sealed class TaskResult<out R> {
    data class Success<out T>(val data: T): TaskResult<T>()
    data class Error(val exception: Exception) : TaskResult<Nothing>()
    object Loading : TaskResult<Nothing>()
}