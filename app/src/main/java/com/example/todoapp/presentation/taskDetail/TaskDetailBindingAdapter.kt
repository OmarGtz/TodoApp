package com.example.todoapp.presentation.taskDetail

import android.view.View
import androidx.databinding.BindingAdapter

/**
 * TaskDetailBindingAdapter
 *
 * @author (c) 2021, UVI TECH SAPI De CV, KAVAK
 */

@BindingAdapter("show")
fun showView(view: View, show: Boolean) {
    view.visibility = if (show) View.VISIBLE else View.GONE
}
