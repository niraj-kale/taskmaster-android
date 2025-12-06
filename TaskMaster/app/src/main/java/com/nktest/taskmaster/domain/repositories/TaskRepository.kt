package com.nktest.taskmaster.domain.repositories

import com.nktest.taskmaster.domain.entities.Task

interface TaskRepository {
    suspend fun create(task: Task): Task
    suspend fun getById(id: String): Task?
    suspend fun getAll(userId: String): List<Task>
    suspend fun update(task: Task): Task
    suspend fun delete(id: String)
}

