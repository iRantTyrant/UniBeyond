package com.example.unibeyond.presentation.auth.login

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

//What uiStates we are gonna look at for changes iin the ui
data class LoginUiState(
    //-- The regular data we need to check for the auth
    val email : String = "",
    val password : String = "",
    //--

    //Show or not the password
    val isPasswordVisible : Boolean = false,

    //Check if its loading
    val isLoading : Boolean = false,

    //Check if there is an error
    val error : String? = null,
)

class LoginViewModel() : ViewModel() {
    //The Mutable data the view model can access this to write new data
    private val _loginUiState = MutableStateFlow(LoginUiState())

    //This is what we use in the ui to read the data from the view model when something changes here we recompose
    val loginUiState : StateFlow<LoginUiState> = _loginUiState.asStateFlow()

    //When  something changes in the Email field we update the state
    fun onEmailChange(email : String) : Unit{
        _loginUiState.value = _loginUiState.value.copy(email = email)
    }

    //When  something changes in the Password field we update the state
    fun onPasswordChange(password : String)  : Unit {
        _loginUiState.value = _loginUiState.value.copy(password = password)
    }

    //When  something changes in the Password field we update the state
    fun onTogglePasswordVisibility() : Unit {
        _loginUiState.value = _loginUiState.value.copy(isPasswordVisible = !loginUiState.value.isPasswordVisible)
    }

    //
    fun onLoginClick(isLoading : Boolean) : Unit {
        _loginUiState.value = _loginUiState.value.copy(isLoading = isLoading)
    }


}