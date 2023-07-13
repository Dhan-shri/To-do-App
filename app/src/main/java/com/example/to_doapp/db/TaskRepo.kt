package com.example.to_doapp.db

class TaskRepo(private val taskDao: TaskDao) {
    val allTask = taskDao.getAllTask()

    suspend fun insertTask(task: Task) : Long {
        return taskDao.insertTaskData(task)
    }

    suspend fun updateTask(task: Task) :Int {
        return taskDao.updateTask(task)
    }

    suspend fun deleteTask(task: Task) :Int {
        return taskDao.deleteTaskData(task)
    }

    suspend fun deleteAllTask() :Int {
        return taskDao.deleteAllTask()
    }
}