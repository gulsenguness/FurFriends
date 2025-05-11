package com.gulsenurgunes.furfriends.data.repository

import android.util.Log
import com.gulsenurgunes.furfriends.common.Resource
import com.gulsenurgunes.furfriends.data.mapper.toDomain
import com.gulsenurgunes.furfriends.data.source.remote.ApiService
import com.gulsenurgunes.furfriends.domain.model.Category
import com.gulsenurgunes.furfriends.domain.repository.CategoryRepository
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val api: ApiService
) : CategoryRepository {
    override suspend fun getCategories(store: String): Resource<List<Category>> =
        try {
            val resp = api.getCategories(store)
            val list = resp.categories.map { it.toDomain() }
            Log.d("CatRepo", "Mapped domains= $list")
            Resource.Success(list)
        } catch (t: Throwable) {
            Resource.Error(t.message ?: "Kategori alınırken hata oldu")
        }
}