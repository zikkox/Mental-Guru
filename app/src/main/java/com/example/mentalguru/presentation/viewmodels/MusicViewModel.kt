package com.example.mentalguru.presentation.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mentalguru.data.model.Music
import com.example.mentalguru.data.repository.MusicRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MusicViewModel : ViewModel() {
    private val repository = MusicRepository()

    private val _musicList = MutableStateFlow<List<Music>>(emptyList())
    val musicList: StateFlow<List<Music>> = _musicList

    private val _music = mutableStateOf<Music?>(null)
    val music: State<Music?> = _music

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun loadMusic() {
        viewModelScope.launch {
            _isLoading.value = true
            _musicList.value = repository.fetchMusicList()
            _isLoading.value = false
        }
    }

    fun getMusicById(id: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _music.value = repository.fetchMusicById(id)
            _isLoading.value = false
        }
    }
}

