package com.example.task_management_app_mad

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.task_management_app_mad.databinding.ActivityUpdateTaskBinding

class UpdateTaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateTaskBinding
    private lateinit var db :TaskDatabaseHelper
    private var taskId:Int = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = TaskDatabaseHelper(this)
        taskId = intent.getIntExtra("task_id",-1)
        if (taskId==-1){
            finish()
            return
        }

        val task = db.getTaskById(taskId)
        binding.updateTitleEditText.setText(task.title)
        binding.updateContentEditText.setText(task.content)
        val statusArray = resources.getStringArray(R.array.spinnerOptions) // Assuming you have a string array resource for status options
        val statusIndex = statusArray.indexOf(task.status)
        if (statusIndex != -1) {
            binding.updateSpinnerStaus.setSelection(statusIndex)
        }

        binding.updateSaveButton.setOnClickListener {
            val newTitle = binding.updateTitleEditText.text.toString()
            val newContent = binding.updateContentEditText.text.toString()
            val newStatus = binding.updateSpinnerStaus.selectedItem.toString()
            val updateTask = Task(taskId,newTitle,newContent,newStatus)
            db.updateTask(updateTask)
            finish()
            Toast.makeText(this,"Task Updated", Toast.LENGTH_SHORT).show()

        }

    }
}