package com.gulsenurgunes.furfriends.di

import com.gulsenurgunes.furfriends.data.repository.CartRepositoryImpl
import com.gulsenurgunes.furfriends.data.repository.CategoryRepositoryImpl
import com.gulsenurgunes.furfriends.data.repository.FavoriteRepositoryImpl
import com.gulsenurgunes.furfriends.data.repository.ProductRepositoryImpl
import com.gulsenurgunes.furfriends.domain.repository.CartRepository
import com.gulsenurgunes.furfriends.domain.repository.CategoryRepository
import com.gulsenurgunes.furfriends.domain.repository.FavoriteRepository
import com.gulsenurgunes.furfriends.domain.repository.ProductRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindCategoryRepository(
        impl: CategoryRepositoryImpl
    ): CategoryRepository

    @Binds
    @Singleton
    abstract fun provideProductRepository(
        impl: ProductRepositoryImpl
    ): ProductRepository

    @Binds
    @Singleton
    abstract fun bindFavoriteRepository(
        impl: FavoriteRepositoryImpl
    ): FavoriteRepository

    @Binds
    @Singleton
    abstract fun bindCartRepository(
        impl: CartRepositoryImpl
    ): CartRepository
}