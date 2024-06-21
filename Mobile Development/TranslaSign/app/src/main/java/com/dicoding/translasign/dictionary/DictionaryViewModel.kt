package com.dicoding.translasign.dictionary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.translasign.data.api.DictionaryData

class DictionaryViewModel : ViewModel() {

    private val _filteredItems = MutableLiveData<List<DictionaryData>>()
    val filteredItems: LiveData<List<DictionaryData>> get() = _filteredItems

    init {
        _filteredItems.value = DictionaryData.alphabet
    }

    fun searchDictionary(query: String?) {
        val filteredList = if (query.isNullOrEmpty()) {
            DictionaryData.alphabet
        } else {
            DictionaryData.alphabet.filter { it.name?.contains(query, true) == true }
        }
        _filteredItems.value = filteredList
    }
}
