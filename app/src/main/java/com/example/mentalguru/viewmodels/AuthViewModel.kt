package com.example.mentalguru.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {

    private val firebaseAuth = FirebaseAuth.getInstance()

    //Loading state (checks if user is already logged in)
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    //Login state
    private val _loginEmail = MutableStateFlow("")
    val loginEmail: StateFlow<String> = _loginEmail

    private val _loginPassword = MutableStateFlow("")
    val loginPassword: StateFlow<String> = _loginPassword

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState


    //Signup state
    private val _signupEmail = MutableStateFlow("")
    val signupEmail: StateFlow<String> = _signupEmail

    private val _signupPassword = MutableStateFlow("")
    val signupPassword: StateFlow<String> = _signupPassword

    private val _signupRepeatPassword = MutableStateFlow("")
    val signupRepeatPassword: StateFlow<String> = _signupRepeatPassword

    private val _signupState = MutableStateFlow<SignupState>(SignupState.Idle)
    val signupState: StateFlow<SignupState> = _signupState


    //Update email/password
    fun onLoginEmailChange(newEmail: String) {
        _loginEmail.value = newEmail
    }

    fun onLoginPasswordChange(newPassword: String) {
        _loginPassword.value = newPassword
    }

    fun onSignupEmailChange(newEmail: String) {
        _signupEmail.value = newEmail
    }

    fun onSignupPasswordChange(newPassword: String) {
        _signupPassword.value = newPassword
    }

    fun onSignupRepeatPasswordChange(newPassword: String) {
        _signupRepeatPassword.value = newPassword
    }

    //Login
    fun login() {
        viewModelScope.launch {
            if (_isLoading.value) return@launch

            if (loginEmail.value.isEmpty() || loginPassword.value.isEmpty()) {
                _loginState.value = LoginState.Error("Email or password cannot be empty")
                return@launch
            }

            _isLoading.value = true

            firebaseAuth.signInWithEmailAndPassword(loginEmail.value, loginPassword.value)
                .addOnCompleteListener { task ->
                    _isLoading.value = false

                    if (task.isSuccessful) {
                        _loginState.value = LoginState.Success
                    } else {
                        _loginState.value =
                            LoginState.Error(task.exception?.message ?: "Login failed")
                    }
                }
        }
    }


    //Signup
    fun signup() {
        viewModelScope.launch {
            if (_isLoading.value) return@launch

            if (signupEmail.value.isEmpty() || signupPassword.value.isEmpty()) {
                _signupState.value = SignupState.Error("Email or password cannot be empty")
                return@launch
            }

            if (signupPassword.value != signupRepeatPassword.value) {
                _signupState.value = SignupState.Error("Passwords do not match")
                return@launch
            }

            _isLoading.value = true

            firebaseAuth.createUserWithEmailAndPassword(signupEmail.value, signupPassword.value)
                .addOnCompleteListener { task ->
                    _isLoading.value = false

                    if (task.isSuccessful) {
                        _signupState.value = SignupState.Success
                    } else {
                        _signupState.value =
                            SignupState.Error(task.exception?.message ?: "Signup failed")
                    }
                }
        }
    }


    //Login state
    sealed class LoginState {
        object Idle : LoginState()
        object Loading : LoginState()
        object Success : LoginState()
        data class Error(val message: String) : LoginState()
    }

    // Signup state
    sealed class SignupState {
        object Idle : SignupState()
        object Loading : SignupState()
        object Success : SignupState()
        data class Error(val message: String) : SignupState()
    }
}