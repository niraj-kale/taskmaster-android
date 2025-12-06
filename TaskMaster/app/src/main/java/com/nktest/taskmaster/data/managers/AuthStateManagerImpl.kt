package com.nktest.taskmaster.data.managers

import com.nktest.taskmaster.core.AuthStateManager
import com.nktest.taskmaster.domain.entities.User
import com.nktest.taskmaster.domain.repositories.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthStateManagerImpl @Inject constructor(
    private val authRepository: AuthRepository
) : AuthStateManager {

    override fun getCurrentUser(): Flow<User?> = flow {
        emit(authRepository.getCurrentUser())
    }

    override suspend fun isAuthenticated(): Boolean {
        return authRepository.getCurrentUser() != null
    }
}

