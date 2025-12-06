package com.nktest.taskmaster.data.repositories

import com.nktest.taskmaster.data.datasources.TaskDataSource
import com.nktest.taskmaster.data.local.TaskDao
import com.nktest.taskmaster.data.local.toDomain
import com.nktest.taskmaster.data.local.toEntity
import com.nktest.taskmaster.data.models.toDto
import com.nktest.taskmaster.data.models.toDomain as taskDtoToDomain
import com.nktest.taskmaster.domain.entities.Task
import com.nktest.taskmaster.domain.repositories.TaskRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val taskDataSource: TaskDataSource,
    private val taskDao: TaskDao
) : TaskRepository {

    override suspend fun create(task: Task): Task {
        val createdTask = taskDataSource.create(task.toDto()).taskDtoToDomain()
        taskDao.insert(createdTask.toEntity())
        return createdTask
    }

    override suspend fun getById(id: String): Task? {
        return taskDao.getById(id)?.toDomain()
    }

    override suspend fun getAll(userId: String): List<Task> {
        return taskDao.getAll(userId).first().map { it.toDomain() }
    }

    override suspend fun update(task: Task): Task {
        val updatedTask = taskDataSource.update(task.toDto()).taskDtoToDomain()
        taskDao.update(updatedTask.toEntity())
        return updatedTask
    }

    override suspend fun delete(id: String) {
        taskDataSource.delete(id)
        taskDao.delete(id)
    }

    suspend fun syncFromFirestore(userId: String) {
        val firestoreTasks = taskDataSource.getAll(userId)
        firestoreTasks.forEach { dto ->
            taskDao.insert(dto.taskDtoToDomain().toEntity())
        }
    }
}

