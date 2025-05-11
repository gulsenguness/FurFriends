package com.gulsenurgunes.furfriends.domain.repository

import com.gulsenurgunes.furfriends.common.Resource
import com.gulsenurgunes.furfriends.domain.model.Category

interface CategoryRepository {
    suspend fun getCategories(store: String): Resource<List<Category>>
}
