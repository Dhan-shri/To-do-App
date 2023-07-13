package com.example.to_doapp.db

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TaskViewModel(private val repo: TaskRepo) : ViewModel() {

    val allTask = repo.allTask

    val inputTaskText = MutableLiveData<String>()
    private val statusMessage = MutableLiveData<Event<String>>()

    fun addTask() { // add only for now
        if (inputTaskText.value == null) {
            statusMessage.value = Event("Please enter user's Name")
        } else {
            val taskText = inputTaskText.value!!
            insertTask(Task(0,taskText))
        }
    }

    fun insertTask(task: Task){
        viewModelScope.launch(Dispatchers.IO){
            val newTask = repo.insertTask(task)
            withContext(Dispatchers.Main){
                if(newTask > -1) {
                    statusMessage.value = Event("User Inserted Successfully! $newTask")
                } else {
                    statusMessage.value = Event("Error occurred")
                }
            }
        }
    }

    fun deleteTask(task: Task){
        viewModelScope.launch(Dispatchers.IO) {
            val task = repo.deleteTask(task)
        }
    }

}