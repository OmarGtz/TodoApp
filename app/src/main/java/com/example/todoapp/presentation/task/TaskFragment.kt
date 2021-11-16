package com.example.todoapp.presentation.task

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.todoapp.R
import com.example.todoapp.TaskApp
import com.example.todoapp.presentation.ViewModelFactory

class TaskFragment : Fragment() {

    private lateinit var taskList: RecyclerView
    private lateinit var emptyView: LinearLayout
    private lateinit var root: View
    private lateinit var mSwipeRefreshLayout: SwipeRefreshLayout
    private lateinit var viewModel: TaskViewModel
    private lateinit var adapter: TaskAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        root = inflater.inflate(R.layout.fragment_task, container, false)
        taskList = root.findViewById(R.id.tasks_list)
        emptyView = root.findViewById(R.id.no_tasks_layout)
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
        adapter = TaskAdapter()
        taskList.adapter = adapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
    }

}