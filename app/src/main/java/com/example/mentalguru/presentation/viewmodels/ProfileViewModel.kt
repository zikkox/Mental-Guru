package com.example.mentalguru.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ProfileViewModel : ViewModel() {

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    private val _userLocation = MutableStateFlow("")
    val userLocation: StateFlow<String> = _userLocation

    private val _userDob = MutableStateFlow("")
    val userDob: StateFlow<String> = _userDob

    private val _isEditDialogVisible = MutableStateFlow(false)
    val isEditDialogVisible: StateFlow<Boolean> = _isEditDialogVisible

    //Load user data from Firestore
    fun loadUserData() {
        val email = getUserEmail() ?: return
        db.collection("users").document(email).get()
            .addOnSuccessListener { document ->
                _userLocation.value = document.getString("location") ?: ""
                _userDob.value = document.getString("dob") ?: ""
            }
    }

    //Save Location in Firestore
    fun updateLocation(newLocation: String) {
        _userLocation.value = newLocation
        val email = getUserEmail() ?: return
        db.collection("users").document(email)
            .set(mapOf("location" to newLocation), SetOptions.merge())
    }

    //Save Date of Birth in Firestore
    fun updateDob(newDob: String) {
        _userDob.value = newDob
        val email = getUserEmail() ?: return
        db.collection("users").document(email)
            .set(mapOf("dob" to newDob), SetOptions.merge())
    }

    fun showEditDialog() {
        _isEditDialogVisible.value = true
    }

    fun hideEditDialog() {
        _isEditDialogVisible.value = false
    }

    init {
        loadUserData()
    }

    private fun getUserEmail(): String? {
        return auth.currentUser?.email
    }
}
