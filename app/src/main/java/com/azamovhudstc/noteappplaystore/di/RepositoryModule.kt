package com.azamovhudstc.noteappplaystore.di

import com.azamovhudstc.noteappplaystore.repo.AppRepository
import com.azamovhudstc.noteappplaystore.repo.imp.AppRepositoryImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun getPostsRepository(impl: AppRepositoryImp): AppRepository


}