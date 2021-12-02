package com.example.todoapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

/**
 * TaskApp
 *
 * @author (c) 2021, UVI TECH SAPI De CV, KAVAK
 */
@HiltAndroidApp
class TaskApp: Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }

}