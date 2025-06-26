package com.gulsenurgunes.furfriends.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.gulsenurgunes.furfriends.domain.model.AddressModel
import com.gulsenurgunes.furfriends.domain.repository.AddressRepository
import javax.inject.Inject

class AddressRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) : AddressRepository {

    override fun saveAddress(address: AddressModel, onResult: (Boolean) -> Unit) {
        val userId = auth.currentUser?.uid ?: return onResult(false)

        firestore.collection("users")
            .document(userId)
            .collection("addresses")
            .add(address)
            .addOnSuccessListener {
                onResult(true)
            }
            .addOnFailureListener { e ->
                onResult(false)
            }
    }

    override fun getAddresses(onResult: (List<AddressModel>) -> Unit) {
        val userId = auth.currentUser?.uid ?: return onResult(emptyList())

        firestore.collection("users")
            .document(userId)
            .collection("addresses")
            .get()
            .addOnSuccessListener { snapshot ->
                val list = snapshot.toObjects(AddressModel::class.java)
                onResult(list)
            }
    }
}