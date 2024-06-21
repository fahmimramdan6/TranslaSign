package com.dicoding.translasign.videotranslator

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.translasign.data.api.ApiConfig
import com.dicoding.translasign.data.api.VideoResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class VideoViewModel : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _result = MutableLiveData<String?>()
    val result: LiveData<String?> = _result

    private val _success = MutableLiveData<Boolean>()
    val success: LiveData<Boolean> = _success

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    fun uploadVideo(videoFile: File) {
        _isLoading.value = true

        val requestFile = videoFile.asRequestBody("video/*".toMediaTypeOrNull())
        val body = MultipartBody.Part.createFormData("video", videoFile.name, requestFile)

        ApiConfig.getApiServiceML().uploadVideo(body).enqueue(object : Callback<VideoResponse> {
            override fun onResponse(
                call: Call<VideoResponse>,
                response: Response<VideoResponse>
            ) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                    val translation = response.body()?.success
                    _result.value = translation
                    _success.value = true
                } else {
                    _isLoading.value = false
                    _success.value = false
                    _message.value = "Translation Failed"
                }
            }

            override fun onFailure(call: Call<VideoResponse>, t: Throwable) {
                Log.e("VideoViewModel", "Error: ${t.message}")
            }
        })
    }
}