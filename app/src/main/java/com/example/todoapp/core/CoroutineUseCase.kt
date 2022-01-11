package com.example.todoapp.core

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

/**
 * CoroutineUseCase
 *
 * @author (c) 2021, UVI TECH SAPI De CV, KAVAK
 */
abstract class CoroutineUseCase<in P, R>(private val coroutineDispatcher: CoroutineDispatcher) {

    suspend operator fun invoke(parameters: P): TaskResult<R> {
        return try {
            withContext(coroutineDispatcher) {
                execute(parameters).let {
                    TaskResult.Success(it)
                }
            }
        } catch (e: Throwable) {
            TaskResult.Error(e)
        }
    }

    abstract suspend fun execute(parameters: P): R

}