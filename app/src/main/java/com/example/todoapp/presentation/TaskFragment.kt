package com.example.todoapp.presentation

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
import com.example.todoapp.presentation.viewmodel.TaskViewModel

class TaskFragment : Fragment() {

    private lateinit var taskList: RecyclerView
    private lateinit var emptyView: LinearLayout
    private lateinit var root: View
    private lateinit var mSwipeRefreshLayout: SwipeRefreshLayout
    private lateinit var viewModel: TaskViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        root = inflater.inflate(R.layout.fragment_task, container, false)
        taskList = root.findViewById(R.id.tasks_list)
        emptyView = root.findViewById(R.id.no_tasks_layout)
        mSwipeRefreshLayout = root.findViewById(R.id.refresh_layout)
        viewModel = ViewModelProvider(this).get(TaskViewModel::class.java)
        return root
    }

    private fun subscribeTasksList() {
        viewModel.items.observe(viewLifecycleOwner) {
            //TODO update list
        }
    }

}