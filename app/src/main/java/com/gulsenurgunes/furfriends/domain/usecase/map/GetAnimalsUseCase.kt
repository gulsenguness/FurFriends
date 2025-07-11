package com.gulsenurgunes.furfriends.domain.usecase.map

import com.gulsenurgunes.furfriends.domain.model.AnimalLocation
import com.gulsenurgunes.furfriends.domain.repository.AnimalRepository
import javax.inject.Inject

class GetAnimalsUseCase @Inject constructor(
    private val repository: AnimalRepository
) {
    suspend operator fun invoke(): List<AnimalLocation> {
        return repository.getAnimals()
    }
}
