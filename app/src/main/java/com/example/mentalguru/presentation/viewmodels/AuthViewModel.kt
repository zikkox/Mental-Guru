package com.example.mentalguru.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
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

    //Snackbar message state
    private val _errorMessage = MutableSharedFlow<String>()
    val errorMessage = _errorMessage.asSharedFlow()

    val currentUser = firebaseAuth.currentUser


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

            _isLoading.value = true

            firebaseAuth.signInWithEmailAndPassword(loginEmail.value, loginPassword.value)
                .addOnCompleteListener { task ->
                    _isLoading.value = false

                    if (task.isSuccessful) {
                        _loginState.value = LoginState.Success
                    } else {
                        val error = "Login failed"
                        _loginState.value = LoginState.Error(error)
                        viewModelScope.launch {
                            _errorMessage.emit(error)
                        }
                    }
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

            _isLoading.value = true

            firebaseAuth.createUserWithEmailAndPassword(signupEmail.value, signupPassword.value)
                .addOnCompleteListener { task ->
                    _isLoading.value = false

                    if (task.isSuccessful) {
                        _signupState.value = SignupState.Success
                    } else {
                        val error = "Signup failed"
                        _signupState.value = SignupState.Error(error)
                        viewModelScope.launch {
                            _errorMessage.emit(error)
                        }
                    }
                }
        }
    }

    //Logout
    fun logout() {
        firebaseAuth.signOut()
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