package com.nktest.taskmaster.core

import com.nktest.taskmaster.domain.entities.User
import kotlinx.coroutines.flow.Flow

interface AuthStateManager {
    fun getCurrentUser(): Flow<User?>
    suspend fun isAuthenticated(): Boolean
}

