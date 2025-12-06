package com.nktest.taskmaster.data.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.nktest.taskmaster.core.AuthStateManager
import com.nktest.taskmaster.data.datasources.AuthDataSource
import com.nktest.taskmaster.data.datasources.FirebaseAuthDataSource
import com.nktest.taskmaster.data.managers.AuthStateManagerImpl
import com.nktest.taskmaster.data.repositories.AuthRepositoryImpl
import com.nktest.taskmaster.domain.repositories.AuthRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    @Singleton
    abstract fun bindAuthDataSource(
        firebaseAuthDataSource: FirebaseAuthDataSource
    ): AuthDataSource

    @Binds
    @Singleton
    abstract fun bindAuthStateManager(
        authStateManagerImpl: AuthStateManagerImpl
    ): AuthStateManager
}

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }
}
