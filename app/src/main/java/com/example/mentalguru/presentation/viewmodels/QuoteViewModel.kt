package com.example.mentalguru.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mentalguru.data.model.Quote
import com.example.mentalguru.data.repository.QuoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class QuoteViewModel : ViewModel() {

    private val repository = QuoteRepository()

    private val _quote = MutableLiveData<Quote?>()
    val quote: LiveData<Quote?> get() = _quote

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun fetchRandomQuote() {
        viewModelScope.launch {
            _isLoading.value = true
            val result = repository.fetchRandomQuote()
            _quote.postValue(result)
            _isLoading.value = false
        }
    }

    fun fetchRandomQuoteByTag(tags: List<String>) {
        viewModelScope.launch {
            _isLoading.value = true
            val tagString = tags.joinToString(",")
            val result = repository.fetchRandomQuoteByTag(tagString)
            _quote.postValue(result)
            _isLoading.value = false
        }
    }

}
