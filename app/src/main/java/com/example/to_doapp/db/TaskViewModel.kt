package com.example.to_doapp.db

import android.text.SpannableString
import android.text.style.StrikethroughSpan
import androidx.lifecycle.LiveData
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
    val message : LiveData<Event<String>>
        get() = statusMessage

    fun addTask() { // add only for now
        if (inputTaskText.value.isNullOrEmpty()) {
            statusMessage.value = Event("Please enter user's Name")
        } else {
            val taskText = inputTaskText.value!!
            insertTask(Task(0,taskText))

            inputTaskText.value = ""
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
            repo.deleteTask(task)
        }
    }

    fun markAsComplete(task: Task){
        viewModelScope.launch {
            val taskName = task.taskText

            val spannableString = SpannableString(taskName)
            spannableString.setSpan(StrikethroughSpan(), 0, spannableString.length, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
    }

}