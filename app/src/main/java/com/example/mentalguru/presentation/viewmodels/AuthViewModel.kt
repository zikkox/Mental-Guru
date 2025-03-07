package com.example.mentalguru.presentation.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.mentalguru.data.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class AuthViewModel(application: Application) : AndroidViewModel(application) {

    val authRepository = AuthRepository(application.applicationContext)
    private val firebaseAuth = FirebaseAuth.getInstance()

    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn

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

    //Snackbar message state
    private val _errorMessage = MutableSharedFlow<String>()
    val errorMessage = _errorMessage.asSharedFlow()

    var currentUser = firebaseAuth.currentUser


    init {
        //Check if user is logged in when ViewModel is created
        if (authRepository.isUserLoggedIn()) {
            _isLoggedIn.value = true
            _loginEmail.value = authRepository.getStoredEmail() ?: ""
        }
    }


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
                val error = "Email or password cannot be empty"
                _loginState.value = LoginState.Error(error)
                _errorMessage.emit(error)
                return@launch
            }

            _loginState.value = LoginState.Loading
            _isLoading.value = true

            try {
                firebaseAuth.signInWithEmailAndPassword(loginEmail.value, loginPassword.value).await()

                _isLoading.value = false
                _loginState.value = LoginState.Success
                authRepository.saveEmail(loginEmail.value)
                authRepository.saveLoginState(true)
            } catch (e: Exception) {
                _isLoading.value = false
                val error = "Login failed: ${e.message}"
                _loginState.value = LoginState.Error(error)
                _errorMessage.emit(error)
            }
        }
    }



    //Signup
    fun signup() {
        viewModelScope.launch {
            if (_isLoading.value) return@launch

            if (signupEmail.value.isEmpty() || signupPassword.value.isEmpty()) {
                val error = "Email or password cannot be empty"
                _errorMessage.emit(error)
                _signupState.value = SignupState.Error(error)
                return@launch
            }

            if (signupPassword.value != signupRepeatPassword.value) {
                val error = "Passwords do not match"
                _errorMessage.emit(error)
                _signupState.value = SignupState.Error(error)
                return@launch
            }

            _signupState.value = SignupState.Loading
            _isLoading.value = true

            try {
                firebaseAuth.createUserWithEmailAndPassword(signupEmail.value, signupPassword.value).await()

                _isLoading.value = false
                _signupState.value = SignupState.Success
                authRepository.saveLoginState(true) // Save login state
            } catch (e: Exception) {
                _isLoading.value = false
                val error = "Signup failed: ${e.message}"
                _signupState.value = SignupState.Error(error)
                _errorMessage.emit(error)
            }
        }
    }


    //Logout
    fun logout() {
        firebaseAuth.signOut()
        _isLoggedIn.value = false
        authRepository.clearUserData()
    }


    //Login state
    sealed class LoginState {
        data object Idle : LoginState()
        data object Loading : LoginState()
        data object Success : LoginState()
        data class Error(val message: String) : LoginState()
    }

    //Signup state
    sealed class SignupState {
        data object Idle : SignupState()
        data object Loading : SignupState()
        data object Success : SignupState()
        data class Error(val message: String) : SignupState()
    }
}