package com.gulsenurgunes.furfriends.domain.usecase

import com.gulsenurgunes.furfriends.common.Resource
import com.gulsenurgunes.furfriends.domain.model.Category
import com.gulsenurgunes.furfriends.domain.repository.CategoryRepository
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(
    private val repo: CategoryRepository
) {
    suspend operator fun invoke(store: String): Resource<List<Category>> =
        repo.getCategories(store)
}