package com.example.todoapp.presentation.task

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.data.room.Task

/**
 * TaskAdapter
 *
 * @author (c) 2021, UVI TECH SAPI De CV, KAVAK
 */
class TaskAdapter: ListAdapter<Task,TaskAdapter.TaskViewHolder>(DIFF_ITEM) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder.provide(parent)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val item: Task = getItem(position)
        holder.bind(item)
    }

    class TaskViewHolder(view: View): RecyclerView.ViewHolder(view) {

        private val title: TextView = itemView.findViewById(R.id.title_text)
        private val checkComplete: CheckBox = itemView.findViewById(R.id.complete_checkbox)

        fun bind(task: Task) {
            title.text = task.title
            checkComplete.isChecked = task.completed
        }

        companion object {
            fun provide(parent: ViewGroup): TaskViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.task_item, parent, false)
                return TaskViewHolder(view)
            }
        }
    }

    companion object {
        private val DIFF_ITEM = object : DiffUtil.ItemCallback<Task>() {
            override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
                return oldItem == newItem
            }

        }
    }

}