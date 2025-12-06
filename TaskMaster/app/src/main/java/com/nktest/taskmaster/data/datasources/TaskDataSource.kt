package com.nktest.taskmaster.data.datasources

import com.nktest.taskmaster.data.models.TaskDto

interface TaskDataSource {
    suspend fun create(task: TaskDto): TaskDto
    suspend fun getById(id: String): TaskDto?
    suspend fun getAll(userId: String): List<TaskDto>
    suspend fun update(task: TaskDto): TaskDto
    suspend fun delete(id: String)
}

