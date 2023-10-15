package com.example.kotlinunittest.localdb

import android.content.Context
import android.util.Log

class AppDatabase private constructor() {
    lateinit var todoDao: TodoDao

    companion object {

        @Volatile
        private lateinit var instance: AppDatabase

        fun buildDB(context: Context): AppDatabase {
//            Log.d("AppDataBase", context.packageName)
            instance = AppDatabase()
            instance?.todoDao = TodoDao()
            return instance
        }

        fun close(){

        }
    }
}

class TodoDao() {
    private var entityList: ArrayList<TodoEntity> = ArrayList()

    suspend fun insert(entity: TodoEntity): Int {
        try {
            entityList.add(entity)
            return 1
        } catch (e: Exception) {
            return 0
        }
    }

    suspend fun delete(entity: TodoEntity): Int {
        try {
            entityList.remove(entity)
            return 1
        } catch (e: Exception) {
            return 0
        }
    }

    suspend fun selectAll(): List<TodoEntity> {
        return entityList
    }
}