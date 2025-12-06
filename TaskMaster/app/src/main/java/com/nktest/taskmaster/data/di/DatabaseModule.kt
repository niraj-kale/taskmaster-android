package com.nktest.taskmaster.data.di

import android.content.Context
import androidx.room.Room
import com.nktest.taskmaster.data.local.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "taskmaster_database"
        ).build()
    }

    @Provides
    fun provideTaskDao(database: AppDatabase) = database.taskDao()
}

