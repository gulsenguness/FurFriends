package com.gulsenurgunes.furfriends.data.source.remote.model

import com.google.gson.annotations.SerializedName

data class CategoryDto(
  @SerializedName("id")
  val id: Int?,

  @SerializedName("name")
  val name: String?,

  @SerializedName("image")
  val imageUrl: String?
)