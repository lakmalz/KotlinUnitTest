package com.example.kotlinunittest

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlinunittest.localdb.AppDatabase
import com.example.kotlinunittest.localdb.TodoDataSource

class MainActivity : AppCompatActivity() {
    private val TAG: String = "MainActivity"

    lateinit var vm: TodoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initials()

        //add data
        vm.addTodo("Wake up", "Start the day")
        vm.addTodo("Take a wash", "Take a shower")
        vm.addTodo("Go to office", "Go for a work")

        //observer
        vm.todoLiveData.observe(this) {
            Log.d(TAG, "List size: ${it.size}")
        }

        // Retrieve the data
        vm.loadTodoList()
    }

    private fun initials(){

        val db = AppDatabase.buildDB(applicationContext)
        val ds = TodoDataSource(db.todoDao)

        vm = TodoViewModel(ds)
    }
}