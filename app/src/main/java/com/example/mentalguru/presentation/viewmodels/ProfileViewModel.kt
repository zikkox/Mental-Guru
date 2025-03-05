package com.example.mentalguru.presentation.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ProfileViewModel : ViewModel() {

    private val _userLocation = MutableStateFlow("")
    val userLocation: StateFlow<String> = _userLocation

    private val _userDob = MutableStateFlow("")
    val userDob: StateFlow<String> = _userDob

    private val _isEditDialogVisible = MutableStateFlow(false)
    val isEditDialogVisible: StateFlow<Boolean> = _isEditDialogVisible

    fun updateLocation(newLocation: String) {
        _userLocation.value = newLocation
    }

    fun updateDob(newDob: String) {
        _userDob.value = newDob
    }

    fun showEditDialog() {
        _isEditDialogVisible.value = true
    }

    fun hideEditDialog() {
        _isEditDialogVisible.value = false
    }
}
