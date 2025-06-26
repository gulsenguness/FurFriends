package com.gulsenurgunes.furfriends.di

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.gulsenurgunes.furfriends.R
import com.gulsenurgunes.furfriends.data.datasource.AuthDataSource
import com.gulsenurgunes.furfriends.data.repository.AuthRepositoryImpl
import com.gulsenurgunes.furfriends.data.repository.FirebaseAuthDataSourceImpl
import com.gulsenurgunes.furfriends.domain.repository.AuthRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
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

        @Provides
        @Singleton
        fun provideFirestore(): FirebaseFirestore =
            FirebaseFirestore.getInstance()

        @Provides
        @Singleton
        fun provideGoogleSignInClient(
            @ApplicationContext context: Context
        ): GoogleSignInClient {
            val gso = GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(context.getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

            return GoogleSignIn.getClient(context, gso)
        }

        @Provides
        @Named("userId")
        fun provideUid(auth: FirebaseAuth) = auth.currentUser?.uid ?: ""
    }
}