package com.example.todoapp.domain

/**
 * TaskDomain
 *
 * @author (c) 2021, UVI TECH SAPI De CV, KAVAK
 */
class TaskDomain(
    var title: String,
    var description: String,
    var completed: Boolean,
    var id: String
) {

    val titleForList: String
    get() = if (title.isNotEmpty()) title else description

    val isActive: Boolean
    get() = !completed

    val isEmpty
    get() = title.isEmpty() && description.isEmpty()

}