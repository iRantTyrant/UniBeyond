package com.example.unibeyond.presentation.clubs

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unibeyond.common.UiState
import com.example.unibeyond.domain.repository.ClubRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClubDetailsViewModel @Inject constructor(
    private val clubRepository: ClubRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    //We dont get the Flow because we are working with only one club
    private val _uiState = MutableStateFlow<UiState<com.example.unibeyond.domain.model.Club>>(UiState.Loading)
    val uiState: StateFlow<UiState<com.example.unibeyond.domain.model.Club>> = _uiState.asStateFlow()

    init {
        // Get club id from navigation
        val clubId = savedStateHandle.get<String>("clubId")

        if (clubId != null) {
            getClubDetails(clubId)
        }
    }

    private fun getClubDetails(id: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading

            val club = clubRepository.getClubById(id) // get club by the id we got from navigation

            if (club != null) {
                _uiState.value = UiState.Success(club)
            } else {
                _uiState.value = UiState.Error("Club not found")
            }
        }
    }
}