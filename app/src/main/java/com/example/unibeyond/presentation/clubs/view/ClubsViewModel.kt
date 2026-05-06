package com.example.unibeyond.presentation.clubs.view

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

//This is a Hilt ViewModel for the ClubsScreen. Hilt regulates which implementation we will use for the ClubRepository.
//We inject said implementation in the constructor so the class can use the implementation to get the data.
@HiltViewModel
class ClubsViewModel @Inject constructor(
    private val clubRepository : ClubRepository
    ): ViewModel() {
        //We give the uiState the constant flow of clubs so it updates when something changes.We map it to become a UiState and as an extention a UiState
    val uiState = clubRepository.getClubs()
        .map {
                if(it.isEmpty())
                {
                    UiState.Error("No clubs found")
                }
                else
                {
                    UiState.Success(it)
                }
            }
        .onStart {emit(UiState.Loading)}//When it starts the UiState is Loading
        .catch {emit(UiState.Error("Error loading clubs"))}//When it doersnt correctly load the UiState is Error
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Companion.WhileSubscribed(5000),
            initialValue = UiState.Loading
        )//Store it in until its destroyed or until its run out (5 seconds)
}