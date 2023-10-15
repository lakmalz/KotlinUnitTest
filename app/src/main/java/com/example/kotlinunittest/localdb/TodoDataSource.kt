package com.example.kotlinunittest.localdb

import kotlinx.coroutines.delay

class TodoDataSource(private var dao: TodoDao) {

    suspend fun getAll(): List<TodoEntity>{
        delay(1000L)
        return dao.selectAll()
    }

    suspend fun addTodo(todo: TodoEntity): Int {
        delay(2000L)
        return dao.insert(todo)
    }
}