package com.gulsenurgunes.furfriends.domain.usecase

import com.gulsenurgunes.furfriends.domain.repository.AuthRepository
import javax.inject.Inject

class SignInWithGoogleUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(idToken: String) =
        authRepository.signInWithGoogle(idToken)
}