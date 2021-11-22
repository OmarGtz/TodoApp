package com.example.todoapp.presentation.addTaks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.todoapp.R
import com.example.todoapp.TaskApp
import com.example.todoapp.myFunction
import com.example.todoapp.presentation.ViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton

/**
 * AddTaskFragment
 *
 * @author (c) 2021, UVI TECH SAPI De CV, KAVAK
 */
class AddTaskFragment : Fragment() {

    private lateinit var titleEdit: EditText
    private lateinit var desEdit: EditText
    private lateinit var doneFabButton: FloatingActionButton
    private lateinit var addTaskVieModel: AddTaskVieModel
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
        val repository = (activity?.applicationContext as TaskApp).taskRepository
        addTaskVieModel =
            ViewModelProvider(this, ViewModelFactory(repository)).get(AddTaskVieModel::class.java)
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