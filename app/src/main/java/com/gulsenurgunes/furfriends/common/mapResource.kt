package com.gulsenurgunes.furfriends.common

inline fun <T, R> Resource<T>.mapResource( //Extention
    transform: (T) -> R
): Resource<R> = when (this) {
    is Resource.Success -> Resource.Success(transform(data))
    is Resource.Error   -> Resource.Error(message)
}
