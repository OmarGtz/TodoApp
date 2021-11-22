package com.example.todoapp.presentation.task

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.todoapp.R
import com.example.todoapp.TaskApp
import com.example.todoapp.myFunction
import com.example.todoapp.presentation.ViewModelFactory
import com.example.todoapp.presentation.taskDetail.TaskDetailFragmentDirections
import com.google.android.material.floatingactionbutton.FloatingActionButton

class TaskFragment : Fragment() {

    private lateinit var taskList: RecyclerView
    private lateinit var emptyView: LinearLayout
    private lateinit var root: View
    private lateinit var mSwipeRefreshLayout: SwipeRefreshLayout
    private lateinit var viewModel: TaskViewModel
    private lateinit var adapter: TaskAdapter
    private lateinit var addFabButton: FloatingActionButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        root = inflater.inflate(R.layout.fragment_task, container, false)
        taskList = root.findViewById(R.id.tasks_list)
        emptyView = root.findViewById(R.id.no_tasks_layout)
        addFabButton = root.findViewById(R.id.add_task_fab)
        mSwipeRefreshLayout = root.findViewById(R.id.refresh_layout)
        mSwipeRefreshLayout.setOnRefreshListener {
            viewModel.loadTasks(true)
            mSwipeRefreshLayout.isRefreshing = false
        }
        initList()
        val repository = (requireActivity().applicationContext as TaskApp).taskRepository
        viewModel = ViewModelProvider(this, ViewModelFactory(repository)).get(TaskViewModel::class.java)
        return root
    }

    private fun initList() {
        adapter = TaskAdapter({ id, completed ->
            viewModel.completedTask(id, completed)
        }) {
            val action = TaskFragmentDirections.actionTaskToTaskDetail(it)
            findNavController().navigate(action)
        }
        taskList.adapter = adapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addFabButton.setOnClickListener {
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
            emptyView.visibility = View.VISIBLE
        }
        myFunction()
    }


}