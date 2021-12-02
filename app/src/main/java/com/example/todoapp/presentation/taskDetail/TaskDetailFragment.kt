package com.example.todoapp.presentation.taskDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.todoapp.R
import com.example.todoapp.TaskApp
import com.example.todoapp.databinding.FragmentTaskDetailBinding
import dagger.hilt.android.AndroidEntryPoint


/**
 * TaskDetailFragment
 *
 * @author (c) 2021, UVI TECH SAPI De CV, KAVAK
 */
@AndroidEntryPoint
class TaskDetailFragment : Fragment() {

    private val detailViewModel: TaskDetailViewModel by viewModels()
    private val args: TaskDetailFragmentArgs by navArgs()
    private lateinit var detailBinding: FragmentTaskDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_task_detail, container, false)
        detailBinding = FragmentTaskDetailBinding.bind(view)
        detailBinding.viewModel = detailViewModel
        detailBinding.lifecycleOwner = viewLifecycleOwner
        detailViewModel.onStart(args.taskId)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //subscribeOnTaskLoaded()
        subscribeOnTaskLoadedError()
        detailBinding.editFab.setOnClickListener {
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

}