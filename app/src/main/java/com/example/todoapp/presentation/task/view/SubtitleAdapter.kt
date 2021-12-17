package com.example.todoapp.presentation.task.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.databinding.SubtitleItemBinding

/**
 * SubtitleAdapter
 *
 * @author (c) 2021, UVI TECH SAPI De CV, KAVAK
 */
class SubtitleAdapter(private val subtitle: String) :
    RecyclerView.Adapter<SubtitleAdapter.SubtitleViewHolder>() {

    var show: Boolean = true
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    class SubtitleViewHolder(private val binding: SubtitleItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(subtitle: String, show: Boolean) {
            with(binding.subtitleText) {
                text = subtitle
                visibility = if (show) View.VISIBLE else View.GONE
            }
        }

        companion object {
            fun create(parent: ViewGroup): SubtitleViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = SubtitleItemBinding.inflate(inflater)
                return SubtitleViewHolder(binding)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubtitleViewHolder {
        return SubtitleViewHolder.create(parent)
    }


    override fun onBindViewHolder(holder: SubtitleViewHolder, position: Int) {
        holder.bind(subtitle, show)
    }

    override fun getItemCount(): Int = if (show) 1 else 0

}