package com.example.to_doapp.db

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "task_data")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val task: String
)
