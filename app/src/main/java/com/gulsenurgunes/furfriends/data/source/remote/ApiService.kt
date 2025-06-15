package com.gulsenurgunes.furfriends.data.source.remote

import com.gulsenurgunes.furfriends.data.source.remote.model.response.BaseResponse
import com.gulsenurgunes.furfriends.data.source.remote.model.response.GetCartProductsResponse
import com.gulsenurgunes.furfriends.data.source.remote.model.response.GetCategoriesResponse
import com.gulsenurgunes.furfriends.data.source.remote.model.response.GetProductDetailResponse
import com.gulsenurgunes.furfriends.data.source.remote.model.response.ProductListDto
import com.gulsenurgunes.furfriends.domain.model.AddToCartBody
import com.gulsenurgunes.furfriends.domain.model.BaseBody
import com.gulsenurgunes.furfriends.domain.model.ClearCartBody
import com.gulsenurgunes.furfriends.domain.model.DeleteFromCartBody
import com.gulsenurgunes.furfriends.domain.model.DeleteFromFavoriteBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @GET("get_products")
    suspend fun getProducts(
        @Header("store") store: String
    ): ProductListDto

    @GET("get_categories")
    suspend fun getCategories(
        @Header("store") store: String
    ): GetCategoriesResponse

    @GET("get_product_detail")
    suspend fun getProductDetail(
        @Header("store") store: String,
        @Query("id") id: Int
    ): GetProductDetailResponse

    @POST("add_to_favorites")
    suspend fun addToFavorites(
        @Header("store") store: String,
        @Body baseBody: BaseBody
    ): BaseResponse

    @GET("get_favorites")
    suspend fun getFavorites(
        @Header("store") store: String ,
        @Query("userId") userId: String
    ): ProductListDto

    @POST("delete_from_favorites")
    suspend fun deleteFromFavorites(
        @Header("store") store: String,
        @Body deleteFromFavoriteBody: DeleteFromFavoriteBody
    ): BaseResponse

    @GET("get_products_by_category")
    suspend fun getProductsByCategory(
        @Header("store") store: String,
        @Query("category") category: String
    ): ProductListDto

    @GET("get_cart_products")
    suspend fun getCartProducts(
        @Header("store") store: String,
        @Query("userId") userId: String
    ): GetCartProductsResponse

    @POST("add_to_cart")
    suspend fun addToCart(
        @Header("store") store: String,
        @Body body: AddToCartBody
    ): BaseResponse

    @POST("delete_from_cart")
    suspend fun deleteFromCart(
        @Header("store") store: String,
        @Body body: DeleteFromCartBody
    ): BaseResponse

    @POST("clear_cart")
    suspend fun clearCart(
        @Header("store") store: String,
        @Body body: ClearCartBody
    ): BaseResponse
}