package com.dicoding.translasign.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.translasign.data.Repository
import com.dicoding.translasign.data.api.ApiConfig
import com.dicoding.translasign.data.api.ErrorResponse
import com.dicoding.translasign.data.pref.UserModel
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

class LoginViewModel(private val repository: Repository) : ViewModel() {
    fun saveSession(user: UserModel) {
        viewModelScope.launch {
            repository.saveSession(user)
        }
    }

    private var _isLoading = MutableLiveData<Boolean>()
    var isLoading: LiveData<Boolean> = _isLoading

    private var _userModel = MutableLiveData<UserModel>()
    var userModel: LiveData<UserModel> = _userModel

    private var _success = MutableLiveData<Boolean>()
    var success: LiveData<Boolean> = _success

    private var _errorMessage = MutableLiveData<String>()
    var errorMessage: LiveData<String> = _errorMessage

    suspend fun login(username: String, password: String) {
        _isLoading.value = true
        try {
            val apiService = ApiConfig.getApiService()
            val successResponse = apiService.login(username, password)
            _userModel.value = UserModel(username, successResponse.token.toString(), true)
            _success.value = true
            _isLoading.value = false
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)
            val errorMessage = errorResponse.message
            _isLoading.value = false
            _errorMessage.value = errorMessage.toString()
            _success.value = false
            Log.e(TAG, "Error: $errorResponse")
        }
    }

    companion object {
        private const val TAG = "LoginViewModel"
    }
}