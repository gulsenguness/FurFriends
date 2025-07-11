package com.gulsenurgunes.furfriends.domain.repository

import com.gulsenurgunes.furfriends.domain.model.AnimalLocation

interface AnimalRepository {
    suspend fun getAnimals(): List<AnimalLocation>
    suspend fun deleteAnimal(animalId: String): Boolean
}
