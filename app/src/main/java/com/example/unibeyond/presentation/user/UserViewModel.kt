package com.example.unibeyond.presentation.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unibeyond.common.UiState
import com.example.unibeyond.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    val uiState = userRepository.getUser("u1")
        .map{
            if(it == null){
                UiState.Error("User not found")
            }
            else{
                UiState.Success(it)
            }

        }
        .onStart { emit(UiState.Loading) }
        .catch { emit(UiState.Error("Error loading user")) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = UiState.Loading
        )

}
