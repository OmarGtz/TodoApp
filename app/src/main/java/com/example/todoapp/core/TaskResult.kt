package com.example.todoapp.core

/**
 * TaskResult
 *
 * @author (c) 2021, UVI TECH SAPI De CV, KAVAK
 */
sealed class TaskResult<out R> {
    data class Success<out T>(val data: T): TaskResult<T>()
    data class Error(val exception: Throwable) : TaskResult<Nothing>()
    object Loading : TaskResult<Nothing>()
}