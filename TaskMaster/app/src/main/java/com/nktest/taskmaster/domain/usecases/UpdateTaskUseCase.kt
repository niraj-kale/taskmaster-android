package com.nktest.taskmaster.domain.usecases

import com.nktest.taskmaster.domain.entities.Task

interface UpdateTaskUseCase {
    suspend fun execute(task: Task): Task
}

