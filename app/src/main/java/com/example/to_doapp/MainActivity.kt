package com.example.to_doapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.style.StrikethroughSpan
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.to_doapp.databinding.ActivityMainBinding
import com.example.to_doapp.databinding.ItemTaskBinding
import com.example.to_doapp.db.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: TaskViewModel
    private lateinit var adapter : TaskAdapter
    private lateinit var mbinding: ItemTaskBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)


        val dao = TaskDatabase.getInstance(applicationContext).taskDao
        val repo = TaskRepo(dao)
        val factory = TaskViewModelFactory(repo)
        viewModel = ViewModelProvider(this, factory)[TaskViewModel::class.java]
        binding.myViewModel = viewModel
        binding.lifecycleOwner = this


        initRecyclerView()
        Log.d("MYTAG", "REcycler init successful in class")

        viewModel.message.observe(this, Observer {
            it.getContentIfNotHandled()?.let {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        })

        binding.btnAdd.setOnClickListener {
            viewModel.addTask()
        }
    }
    private fun displayTaskList(){
        viewModel.allTask.observe(this){
            Log.i("MYTAG", it.toString())
            adapter.setList(it)
            adapter.notifyDataSetChanged()
        }
    }

    private fun initRecyclerView(){
        Log.d("MYTAG", "Recycler init successful")
        binding.taskRecyclerView.layoutManager = LinearLayoutManager(this)
        adapter  = TaskAdapter { selectedItem: Task, view: View ->
            listItemClicked(view, selectedItem)
        }

        displayTaskList()
        binding.taskRecyclerView.adapter = adapter

    }

    private fun listItemClicked(view: View, task: Task){
        Toast.makeText(this, "Selected name is ${task.taskText}", Toast.LENGTH_LONG).show()
        val recyclerView = binding.taskRecyclerView
        val itemPosition = recyclerView.indexOfChild(view) // Assuming 'view' is the item view clicked
        showUpPopMenu(view, itemPosition, task)

    }


    private fun showUpPopMenu(view: View, itemPosition: Int, task: Task){
        runOnUiThread{
            val popup = PopupMenu(applicationContext, view)
            popup.menuInflater.inflate(R.menu.alert_dialog, popup.menu)

            val gravity = Gravity.END
            popup.gravity = gravity
            popup.setOnMenuItemClickListener {item ->
                when(item.itemId){
                    R.id.delete -> {
                        Toast.makeText(applicationContext, "Selected name is", Toast.LENGTH_LONG).show()
                        viewModel.deleteTask(task)
                    }
                    R.id.markAsCompleted -> {
                        val spannableString = SpannableString(task.taskText)
                        spannableString.setSpan(StrikethroughSpan(), 0, spannableString.length, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE)
                    }
            }
            true
        }
        popup.show()
    }
   }

}