package com.dicoding.translasign

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.translasign.dashboard.DashboardViewModel
import com.dicoding.translasign.data.Repository
import com.dicoding.translasign.data.di.Injection
import com.dicoding.translasign.login.LoginViewModel
import com.dicoding.translasign.profile.ProfileViewModel
import com.dicoding.translasign.register.RegisterViewModel
import com.dicoding.translasign.splashscreen.CustomSplashViewModel
import com.dicoding.translasign.videotranslator.VideoViewModel

class ViewModelFactory(private val repository: Repository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(DashboardViewModel::class.java) -> {
                DashboardViewModel(repository) as T
            }

            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(repository) as T
            }

            modelClass.isAssignableFrom(CustomSplashViewModel::class.java) -> {
                CustomSplashViewModel(repository) as T
            }

            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel() as T
            }

            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
                ProfileViewModel(repository) as T
            }

            modelClass.isAssignableFrom(VideoViewModel::class.java) -> {
                VideoViewModel() as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class: $modelClass")
        }
    }

    companion object {
        fun getInstance(context: Context) = ViewModelFactory(Injection.provideRepository(context))
    }
}
