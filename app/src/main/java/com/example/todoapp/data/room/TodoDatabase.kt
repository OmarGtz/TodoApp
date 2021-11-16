package com.example.todoapp.data.room

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * TodoDatabase
 *
 * @author (c) 2021, UVI TECH SAPI De CV, KAVAK
 */

@Database(entities = [Task::class], version = 1, exportSchema = false)
abstract class TodoDatabase: RoomDatabase() {
    abstract fun taskDao(): TaskDao
}