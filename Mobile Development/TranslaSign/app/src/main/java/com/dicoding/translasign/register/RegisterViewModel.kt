package com.dicoding.translasign.register

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.translasign.data.api.ApiConfig
import com.dicoding.translasign.data.api.ErrorResponse
import com.google.gson.Gson
import retrofit2.HttpException

class RegisterViewModel : ViewModel() {

    private var _message = MutableLiveData<String>()
    var message: LiveData<String> = _message

    private var _success = MutableLiveData<Boolean>()
    var success: LiveData<Boolean> = _success

    private var _isLoading = MutableLiveData<Boolean>()
    var isLoading: LiveData<Boolean> = _isLoading

    suspend fun register(name: String, username: String, email: String, password: String) {
        _isLoading.value = true
        try {
            val apiService = ApiConfig.getApiService()
            val successResponse = apiService.register(name, username, email, password)
            _isLoading.value = false
            _message.value = successResponse.status.toString()
            _success.value = true
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)
            val errorMessage = errorResponse.message
            _isLoading.value = false
            _message.value = errorMessage.toString()
            _success.value = false
            Log.e(TAG, "Error: $errorResponse")
        }
    }

    companion object {
        private const val TAG = "RegisterViewModel"
    }
}