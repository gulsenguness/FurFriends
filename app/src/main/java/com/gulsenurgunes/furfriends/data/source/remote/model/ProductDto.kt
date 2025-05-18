package com.gulsenurgunes.furfriends.data.source.remote.model

import com.google.gson.annotations.SerializedName

data class ProductDto(
    @SerializedName("id")           val id: Int?,
    @SerializedName("title")        val title: String?,
    @SerializedName("price")        val price: Double?,
    @SerializedName("salePrice")    val salePrice: Double?,
    @SerializedName("saleState")    val saleState: Boolean?,
    @SerializedName("rate")         val rate: Double?,
    @SerializedName("count")        val count: Int?,
    @SerializedName("description")  val description: String?,
    @SerializedName("imageOne")     val imageOne: String?,
    @SerializedName("imageTwo")     val imageTwo: String?,
    @SerializedName("imageThree")   val imageThree: String?,
    @SerializedName("category")     val category: String?
)