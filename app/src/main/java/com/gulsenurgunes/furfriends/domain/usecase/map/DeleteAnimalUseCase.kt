package com.gulsenurgunes.furfriends.domain.usecase.map

import com.gulsenurgunes.furfriends.domain.repository.AnimalRepository
import javax.inject.Inject

class DeleteAnimalUseCase @Inject constructor(
    private val repository: AnimalRepository
) {
    suspend operator fun invoke(animalId: String): Boolean {
        return repository.deleteAnimal(animalId)
    }
}