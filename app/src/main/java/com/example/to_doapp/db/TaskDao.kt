package com.example.to_doapp.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TaskDao {

    @Insert
    suspend fun insertTaskData(task: Task) :Long

    @Delete
    suspend fun deleteTaskData(task: Task) : Int

    @Update
    suspend fun updateTask(task: Task) : Int

    @Query("DELETE FROM task_data")
    suspend fun deleteAllTask() : Int

    @Query("SELECT * FROM task_data")
    fun getAllTask() : LiveData<List<Task>>

}