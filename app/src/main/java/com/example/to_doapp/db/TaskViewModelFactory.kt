package com.example.to_doapp.db

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class TaskViewModelFactory(private val taskRepo: TaskRepo): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TaskViewModel::class.java)){
            return TaskViewModel(taskRepo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}