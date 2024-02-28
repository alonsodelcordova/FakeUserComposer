package com.jacs.cordova.fakeusercomposer.di

import com.jacs.cordova.fakeusercomposer.repository.UserRepository
import com.jacs.cordova.fakeusercomposer.repository.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun userRepository(repo: UserRepositoryImpl) : UserRepository



}