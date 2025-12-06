package com.nktest.taskmaster.domain.usecases

import com.nktest.taskmaster.domain.entities.Task

interface CreateTaskUseCase {
    suspend fun execute(task: Task): Task
}

