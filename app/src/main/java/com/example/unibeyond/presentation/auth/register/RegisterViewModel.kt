package com.example.unibeyond.presentation.auth.register


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

//What uiStates we are gonna look at for changes in the ui
data class RegisterUiState(
    val fullName : String = "",
    val major : String = "",
    val year : String = "",
    val email: String = "",
    val password : String = "",
    val isLoading : Boolean = false,
    val errorMessage : String? = null,
    val isPasswordVisible : Boolean = false,
    val isSuccess : Boolean = false
)

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repository: AuthRepository
): ViewModel(){
    //The Mutable data the view model can access this to write new data
    private val _registerUiState = MutableStateFlow(RegisterUiState())

    //This is what we use in the ui to read the data from the view model when something changes here we recompose
    val registerUiState : StateFlow<RegisterUiState> = _registerUiState.asStateFlow()

    //Recompose when we type something in the full name field
    fun onFullNameChange(newValue : String){
        _registerUiState.value = _registerUiState.value.copy(fullName = newValue)
    }

    //Recompose when we type something in the major field
    fun onMajorChange(newValue : String){
        _registerUiState.value = _registerUiState.value.copy(major = newValue)
    }

    //Recompose when we type something in the year field
    fun onYearChange(newValue : String){
        _registerUiState.value = _registerUiState.value.copy(year = newValue)
    }

    //Recompose when we type something in the email field
    fun onEmailChange(newValue : String){
        _registerUiState.value = _registerUiState.value.copy(email = newValue)
    }

    //Recompose when we type something in the password field
    fun onPasswordChange(newValue : String){
        _registerUiState.value = _registerUiState.value.copy(password = newValue)
    }

    fun onTogglePasswordVisibility() {
        _registerUiState.value =
            _registerUiState.value.copy(isPasswordVisible = !registerUiState.value.isPasswordVisible)
    }

    fun onRegisterClick() {
        //We get the current state
        val currentState = _registerUiState.value

        //ViewModel Verification

        // CHeck for blank inputs
        if (currentState.email.isBlank() || currentState.password.isBlank()) {
            _registerUiState.update { it.copy(errorMessage = "Please fill all fields") }
            return
        }

        // Check for valid email
        if (!currentState.email.contains("@")) {
            _registerUiState.update { it.copy(errorMessage = "Invalid email format") }
            return
        }

        //Check for password length
        if (currentState.password.length < 6) {
            _registerUiState.update { it.copy(errorMessage = "Password too short (min 6 chars)") }
            return
        }
        viewModelScope.launch {

            //Sets isLoading = true
            _registerUiState.value = _registerUiState.value.copy(isLoading = true)



            //We use the repository to register the user
            val result = repository.register(
                email = currentState.email,
                password = currentState.password,
                fullName = currentState.fullName,
                major = currentState.major,
                year = currentState.year
            )

            //If the result is successful
            if (result.isSuccess) {
                println("Register Successful!")
                _registerUiState.value =
                    _registerUiState.value.copy(isLoading = false, errorMessage = null, isSuccess = true)
            }
            else {
                println("Error: ${result.exceptionOrNull()?.message}")
                _registerUiState.update{
                    it.copy(
                        isLoading=false,
                        errorMessage ="Registration Failed"
                    )
                }

            }
        }

    }


}