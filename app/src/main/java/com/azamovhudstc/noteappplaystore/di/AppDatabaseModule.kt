package com.azamovhudstc.noteappplaystore.di

import android.content.Context
import androidx.room.Room
import com.azamovhudstc.noteappplaystore.data.local.dao.NoteDao
import com.azamovhudstc.noteappplaystore.data.local.database.NoteDatabase

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppDatabaseModule {
    @[Provides Singleton]
    fun provideVideosDatabase(@ApplicationContext context: Context): NoteDatabase {
        return Room.databaseBuilder(context, NoteDatabase::class.java, "animalWiki.db")
            .allowMainThreadQueries()
            .build()

    }

    @Provides
    fun provideVideosDao(appDatabase: NoteDatabase): NoteDao {
        return appDatabase.bookDao()
    }
}
