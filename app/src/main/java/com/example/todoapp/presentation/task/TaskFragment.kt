package com.example.todoapp.presentation.task

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.todoapp.R
import com.example.todoapp.databinding.FragmentTaskBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TaskFragment : Fragment() {

    private lateinit var root: View
    private val viewModel: TaskViewModel by viewModels()
    private lateinit var adapter: TaskAdapter
    private var taskBinding: FragmentTaskBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        root = inflater.inflate(R.layout.fragment_task, container, false)
        taskBinding = FragmentTaskBinding.bind(root)
        taskBinding?.refreshLayout?.setOnRefreshListener {
            viewModel.loadTasks(true)
            taskBinding?.refreshLayout?.isRefreshing = false
        }

        initList()
        return root
    }

    private fun initList() {
        adapter = TaskAdapter({ id, completed ->
            viewModel.completedTask(id, completed)
        }) {
            val action = TaskFragmentDirections.actionTaskToTaskDetail(it)
            findNavController().navigate(action)
        }
        taskBinding?.tasksList?.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        taskBinding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        taskBinding?.addTaskFab?.setOnClickListener {
            val action = TaskFragmentDirections.actionTaskFragmentToAddTaskFragment(
                "",
                getString(R.string.add_task_title)
            )
            findNavController().navigate(action)
        }
        subscribeTasksList()
        subscribeEmptyListError()
    }

    private fun subscribeTasksList() {
        viewModel.items.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    private fun subscribeEmptyListError() {
        viewModel.emptyListError.observe(viewLifecycleOwner) {
            taskBinding?.emptyLayout?.noTasksLayout?.visibility = View.VISIBLE
        }
    }

}