package com.example.unibeyond.presentation.clubs


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unibeyond.common.UiState
import com.example.unibeyond.domain.repository.ClubRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MyClubsViewModel @Inject constructor(
    private val clubRepository: ClubRepository
) : ViewModel() {

    // Get the CLubs for each user we use u1 for example using the function of the Club View model
    val uiState = clubRepository.getClubsForUser("u1")
        .map {
            if (it.isEmpty()) {
                UiState.Error("You haven't joined any clubs yet.")
            } else {
                UiState.Success(it)
            }
        }
        .onStart { emit(UiState.Loading) }
        .catch { emit(UiState.Error("Error loading your clubs")) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = UiState.Loading
        )
}