package com.dicoding.translasign.data.di

import android.content.Context
import com.dicoding.translasign.data.Repository
import com.dicoding.translasign.data.pref.UserPreference
import com.dicoding.translasign.data.pref.dataStore

object Injection {
    fun provideRepository(context: Context): Repository {
        val pref = UserPreference.getInstance(context.dataStore)
        return Repository.getInstance(pref)
    }
}