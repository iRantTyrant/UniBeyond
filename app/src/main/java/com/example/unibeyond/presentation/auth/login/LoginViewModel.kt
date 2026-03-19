package com.example.unibeyond.presentation.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unibeyond.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

//Data class to set what a LoginUiState should have
data class LoginUiState(
    val email : String = "",
    val password : String = "",
    val isPasswordVisible : Boolean = false,
    val isLoading : Boolean = false,
    val error : String? = null,
    val isSuccess : Boolean = false
)

@HiltViewModel //We use inject to build the LoginViewModel with the repository
class LoginViewModel @Inject constructor(
    private val repository: AuthRepository
): ViewModel() {

    private val _loginUiState = MutableStateFlow(LoginUiState())
    val loginUiState : StateFlow<LoginUiState> = _loginUiState.asStateFlow()

    fun onEmailChange(email : String) {
        _loginUiState.update { it.copy(email = email) }
    }

    fun onPasswordChange(password : String) {
        _loginUiState.update { it.copy(password = password) }
    }

    fun onTogglePasswordVisibility() {
        _loginUiState.update { it.copy(isPasswordVisible = !it.isPasswordVisible) }
    }

    //Login button click
    fun onLoginClick() {
        viewModelScope.launch {


            // show loading
            _loginUiState.update { it.copy(isLoading = true, error = null) }

            // get the data from the ui
            val emailToSend = _loginUiState.value.email
            val passwordToSend = _loginUiState.value.password

            // call the repository
            val result = repository.login(emailToSend, passwordToSend)

            //check if it was a success
            if (result.isSuccess) {
                println("Login Successful!")
                // stop loading
                _loginUiState.update { it.copy(isLoading = false, isSuccess = true) }
                //
            } else {
                val errorMsg = result.exceptionOrNull()?.message ?: "Unknown Error"
                println("Error: $errorMsg")

                //Update ui in case of error
                _loginUiState.update {
                    it.copy(
                        isLoading = false,
                        error = errorMsg
                    )
                }
            }
        }
    }
}