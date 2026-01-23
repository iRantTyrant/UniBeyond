package com.example.unibeyond.common


//We will use this to give feedback to the user as to what is happening
sealed interface UiState<out T> {

    //State 1 Loading
    data object Loading : UiState<Nothing>

    //2 Success
    data class Success<T>(val data : T) : UiState<T>

    //State 3 Error
    data class Error(val message: String) : UiState<Nothing>


}