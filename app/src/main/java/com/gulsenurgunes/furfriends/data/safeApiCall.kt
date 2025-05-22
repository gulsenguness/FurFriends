package com.gulsenurgunes.furfriends.data

import com.gulsenurgunes.furfriends.common.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

suspend inline fun <T> safeApiCall(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    crossinline apiCall: suspend () -> T
): Resource<T> = withContext(dispatcher) {
    try {
        Resource.Success(apiCall())
    } catch (e: HttpException) {
        val msg = e.response()?.errorBody()?.string() ?: "Sunucu hatası (${e.code()})"
        Resource.Error(msg)
    } catch (e: IOException) {
        Resource.Error("Ağ hatası: ${e.localizedMessage ?: "bilinmiyor"}")
    } catch (e: Exception) {
        Resource.Error("Beklenmeyen hata: ${e.localizedMessage ?: "??? 🤯"}")
    }
}
