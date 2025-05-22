package com.gulsenurgunes.furfriends.data.repository

import com.gulsenurgunes.furfriends.common.Resource
import com.gulsenurgunes.furfriends.common.mapResource
import com.gulsenurgunes.furfriends.data.mapper.toDomain
import com.gulsenurgunes.furfriends.data.safeApiCall
import com.gulsenurgunes.furfriends.data.source.remote.ApiService
import com.gulsenurgunes.furfriends.domain.model.Category
import com.gulsenurgunes.furfriends.domain.repository.CategoryRepository
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val api: ApiService
) : CategoryRepository {
    override suspend fun getCategories(store: String): Resource<List<Category>> =
        safeApiCall { api.getCategories(store) }
            .mapResource { dto->
                dto.categories.map { it.toDomain() }
            }
}