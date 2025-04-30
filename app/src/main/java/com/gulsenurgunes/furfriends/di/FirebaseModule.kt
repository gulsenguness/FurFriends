package com.gulsenurgunes.furfriends.di

import com.google.firebase.auth.FirebaseAuth
import com.gulsenurgunes.furfriends.data.datasource.AuthDataSource
import com.gulsenurgunes.furfriends.data.repository.AuthRepositoryImpl
import com.gulsenurgunes.furfriends.data.repository.FirebaseAuthDataSourceImpl
import com.gulsenurgunes.furfriends.domain.repository.AuthRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class FirebaseModule {

    @Binds
    abstract fun bindAuthDataSource(
        impl: FirebaseAuthDataSourceImpl
    ): AuthDataSource

    @Binds
    abstract fun bindAuthRepository(
        impl: AuthRepositoryImpl
    ): AuthRepository

    companion object {
        @Provides
        @Singleton
        fun provideFirebaseAuth(): FirebaseAuth =
            FirebaseAuth.getInstance()
    }
}