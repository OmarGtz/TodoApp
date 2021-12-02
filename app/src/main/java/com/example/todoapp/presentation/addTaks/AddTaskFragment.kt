package com.example.todoapp.presentation.addTaks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.todoapp.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint

/**
 * AddTaskFragment
 *
 * @author (c) 2021, UVI TECH SAPI De CV, KAVAK
 */
@AndroidEntryPoint
class AddTaskFragment : Fragment() {

    private lateinit var titleEdit: EditText
    private lateinit var desEdit: EditText
    private lateinit var doneFabButton: FloatingActionButton
    private val addTaskVieModel: AddTaskVieModel by viewModels()
    private val args: AddTaskFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_task, container, false)
        titleEdit = view.findViewById(R.id.title_edit_text)
        desEdit = view.findViewById(R.id.descrip_edit_text)
        doneFabButton = view.findViewById(R.id.done_fab)
        addTaskVieModel.onStart(args.taskId)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeAddTaskSuccess()
        subscribeOnTaskLoaded()
        subscribeUpdateTaskSuccess()
        doneFabButton.setOnClickListener {
            val title = titleEdit.text.toString()
            val desc = desEdit.text.toString()
            addTaskVieModel.saveTask(title, desc)
        }
    }

    private fun subscribeAddTaskSuccess() {
        addTaskVieModel.addTaskSuccess.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), "Tarea agregada", Toast.LENGTH_SHORT).show()
            navigateToHome()
        }
    }

    private fun subscribeUpdateTaskSuccess() {
        addTaskVieModel.updateTaskSuccess.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), "Tarea actualizada", Toast.LENGTH_SHORT).show()
            navigateToHome()
        }
    }

    private fun navigateToHome() {
        findNavController().navigate(R.id.action_addTaskFragment_to_taskFragment)
    }

    private fun subscribeOnTaskLoaded() {
        addTaskVieModel.onTaskLoaded.observe(viewLifecycleOwner) {
            titleEdit.setText(it.title)
            desEdit.setText(it.description)
        }
    }
}