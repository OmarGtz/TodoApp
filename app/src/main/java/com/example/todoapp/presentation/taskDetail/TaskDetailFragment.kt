package com.example.todoapp.presentation.taskDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.example.todoapp.R
import com.example.todoapp.TaskApp
import com.example.todoapp.presentation.ViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton

/**
 * TaskDetailFragment
 *
 * @author (c) 2021, UVI TECH SAPI De CV, KAVAK
 */
class TaskDetailFragment: Fragment() {

    private lateinit var titleText: TextView
    private lateinit var descText: TextView
    private lateinit var editFab:  FloatingActionButton
    private lateinit var detailViewModel: TaskDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_task_detail, container, false)
        titleText = view.findViewById(R.id.title_text)
        descText = view.findViewById(R.id.desc_text)
        editFab = view.findViewById(R.id.edit_fab)
        val repository = (activity?.applicationContext as TaskApp).taskRepository
        detailViewModel = ViewModelProvider(this, ViewModelFactory(repository)).get(TaskDetailViewModel::class.java)
        return view
    }



}