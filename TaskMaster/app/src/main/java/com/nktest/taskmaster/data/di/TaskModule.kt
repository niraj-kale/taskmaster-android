package com.nktest.taskmaster.data.di

import com.nktest.taskmaster.data.datasources.FirestoreTaskDataSource
import com.nktest.taskmaster.data.datasources.TaskDataSource
import com.nktest.taskmaster.data.repositories.TaskRepositoryImpl
import com.nktest.taskmaster.domain.repositories.TaskRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class TaskModule {

    @Binds
    @Singleton
    abstract fun bindTaskRepository(
        taskRepositoryImpl: TaskRepositoryImpl
    ): TaskRepository

    @Binds
    @Singleton
    abstract fun bindTaskDataSource(
        firestoreTaskDataSource: FirestoreTaskDataSource
    ): TaskDataSource
}

