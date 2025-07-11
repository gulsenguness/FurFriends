package com.gulsenurgunes.furfriends.data.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.gulsenurgunes.furfriends.domain.model.AnimalLocation
import com.gulsenurgunes.furfriends.domain.repository.AnimalRepository
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AnimalRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : AnimalRepository {

    private val animalCollection = firestore.collection("animals")

    override suspend fun getAnimals(): List<AnimalLocation> {
        return try {
            val snapshot = animalCollection.get().await()
            snapshot.documents.mapNotNull { doc ->
                doc.toObject(AnimalLocation::class.java)?.copy(id = doc.id)
            }
        } catch (e: Exception) {
            Log.e("AnimalRepository", "Firebase verisi çekilemedi: ${e.message}")
            emptyList()
        }
    }

    override suspend fun deleteAnimal(animalId: String): Boolean {
        return try {
            animalCollection.document(animalId).delete().await()
            true
        } catch (e: Exception) {
            false
        }
    }
}
