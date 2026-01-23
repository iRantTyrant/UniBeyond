package com.example.unibeyond.presentation.events

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unibeyond.common.UiState
import com.example.unibeyond.domain.repository.EventRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

//This is a Hilt ViewModel for the EventsScreen. Hilt regulates which implementation we will use for the EventRepository.
//We inject said implementation in the constructor so the calss can use the implementation to get the data.
@HiltViewModel
class EventsViewModel @Inject constructor(
    private val eventRepository: EventRepository
) : ViewModel(){
    val uiState = eventRepository.getEvents()
        .map{
            if (it.isEmpty()){UiState.Error("No events found")}
            else{
            UiState.Success(it)
            }
        }
        .onStart {emit(UiState.Loading)} //When we start the program the Ui State is loading
        .catch {emit(UiState.Error("Error loading events"))} // If something goes wrong the UiState is now Error
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = UiState.Loading
        )//Keep the data stored in the viewmodel until its destroyed or until its run out (5 seconds)
}

