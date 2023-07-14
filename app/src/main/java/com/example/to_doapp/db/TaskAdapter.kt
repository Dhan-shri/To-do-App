package com.example.to_doapp.db

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.to_doapp.R
import com.example.to_doapp.databinding.ItemTaskBinding

class TaskAdapter( private val clickListener: (Task, View) -> Unit) : RecyclerView.Adapter<MyViewHolder> () {

    private val taskList = ArrayList<Task>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemTaskBinding = DataBindingUtil.inflate(layoutInflater, R.layout.item_task, parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(taskList[position], clickListener)
    }

    fun setList(task : List<Task>){
        taskList.clear()
        taskList.addAll(task)
    }
}




class MyViewHolder (private val binding  : ItemTaskBinding) : RecyclerView.ViewHolder(binding.root){
    fun bind(task: Task, clickListener: (Task, View) -> Unit){
        binding.checkBox.text = task.taskText

        binding.checkLayout.setOnClickListener {
            clickListener(task, binding.checkLayout)
        }
    }
}