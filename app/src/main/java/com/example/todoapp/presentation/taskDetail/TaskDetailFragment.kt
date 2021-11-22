package com.example.todoapp.presentation.taskDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
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
    private lateinit var checkCompleted: CheckBox
    private lateinit var detailViewModel: TaskDetailViewModel
    private val args: TaskDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_task_detail, container, false)
        titleText = view.findViewById(R.id.title_text)
        descText = view.findViewById(R.id.desc_text)
        editFab = view.findViewById(R.id.edit_fab)
        checkCompleted = view.findViewById(R.id.checkbox_completed)
        val repository = (activity?.applicationContext as TaskApp).taskRepository
        detailViewModel = ViewModelProvider(this, ViewModelFactory(repository)).get(TaskDetailViewModel::class.java)
        detailViewModel.onStart(args.taskId)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeOnTaskLoaded()
        subscribeOnTaskLoadedError()
        editFab.setOnClickListener {
            val action = TaskDetailFragmentDirections.actionTaskDetailFragmentToAddTaskFragment(
                args.taskId,
                getString(R.string.edit_task_title)
            )
            findNavController().navigate(action)
        }
    }

    private fun subscribeOnTaskLoadedError() {
        detailViewModel.taskError.observe(viewLifecycleOwner) { messageError ->
            Toast.makeText(requireContext(), getString(messageError), Toast.LENGTH_SHORT).show()
        }
    }

    private fun subscribeOnTaskLoaded() {
        detailViewModel.taskDetail.observe(viewLifecycleOwner) { task ->
            titleText.text = task.title
            descText.text = task.description
            checkCompleted.isChecked = task.completed
        }
    }

}