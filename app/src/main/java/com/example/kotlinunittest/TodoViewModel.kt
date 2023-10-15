package com.example.kotlinunittest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlinunittest.localdb.TodoDataSource
import com.example.kotlinunittest.localdb.TodoEntity
import com.example.kotlinunittest.utils.Validator
import kotlinx.coroutines.launch

class TodoViewModel(private val dataSource: TodoDataSource) : ViewModel() {

    private val _todoLiveData = MutableLiveData<List<TodoEntity>>()
    val todoLiveData: LiveData<List<TodoEntity>>
        get() = _todoLiveData

    fun addTodo(title: String, content: String) = viewModelScope.launch {
        dataSource.addTodo(TodoEntity(title, content))
    }

    fun loadTodoList() = viewModelScope.launch {
        _todoLiveData.value = dataSource.getAll()
    }
}