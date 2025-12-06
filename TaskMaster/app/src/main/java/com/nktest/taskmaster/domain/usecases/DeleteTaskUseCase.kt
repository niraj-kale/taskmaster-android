package com.nktest.taskmaster.domain.usecases

interface DeleteTaskUseCase {
    suspend fun execute(id: String)
}

