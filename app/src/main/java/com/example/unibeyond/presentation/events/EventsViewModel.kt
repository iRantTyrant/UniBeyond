package com.example.unibeyond.presentation.events

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unibeyond.common.UiState
import com.example.unibeyond.domain.model.Club
import com.example.unibeyond.domain.model.Event
import com.example.unibeyond.domain.repository.ClubRepository
import com.example.unibeyond.domain.repository.EventRepository
import com.example.unibeyond.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class EventDetailsStateData(
    val event: Event,
    val club: Club
)

//This is a Hilt ViewModel for the EventsScreen. Hilt regulates which implementation we will use for the EventRepository.
//We inject said implementation in the constructor so the class can use the implementation to get the data.
@HiltViewModel
class EventsViewModel @Inject constructor(
    private val eventRepository: EventRepository,
    private val userRepository: UserRepository,
    private val clubRepository: ClubRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    //All events
    val uiState = eventRepository.getEvents()
        .map {
            if (it.isEmpty()) { UiState.Error("No events found") }
            else {
                UiState.Success(it)
            }
        }
        .onStart { emit(UiState.Loading) } //When we start the program the Ui State is loading
        .catch { emit(UiState.Error("Error loading events")) } // If something goes wrong the UiState is now Error
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = UiState.Loading
        )//Keep the data stored in the viewmodel until its destroyed or until its run out (5 seconds)

    //EventId
    val eventId = savedStateHandle.get<String>("eventId") ?: ""

    //Event details state private
    private val _eventDetailsState = MutableStateFlow<UiState<EventDetailsStateData>>(UiState.Loading)

    //Event Details state ui and public read only
    val eventDetailsState = _eventDetailsState.asStateFlow()

    init {
        viewModelScope.launch {
            fetchAndRefreshDetails()
        }
    }

    // Function for joining an event
    fun joinEvent(eventId: String) {
        viewModelScope.launch {
            // Hardcoded "u1" as the current logged-in user for your mock data
            eventRepository.toggleInterest(
                eventId,
                userId = "u1",
                isInterested = true
            )
            // Refresh the screen details after modifying data
            fetchAndRefreshDetails()
        }
    }

    // Function for leaving an event
    fun leaveEvent(eventId: String) {
        viewModelScope.launch {
            // Hardcoded "u1" as the current logged-in user for your mock data
            eventRepository.toggleInterest(eventId, "u1",false)
            // Refresh the screen details after modifying data
            fetchAndRefreshDetails()
        }
    }


    // Helper function to fetch the event, map its club, and update the UI state
    private suspend fun fetchAndRefreshDetails() {
        val result = eventRepository.getEventById(eventId)
        if (result != null) {
            val clubId = result.clubId //Get which club id is hosting
            val club = clubRepository.getClubById(clubId) //Match the id with a club
            if (club != null) {
                val packet = EventDetailsStateData(result, club)
                _eventDetailsState.value = UiState.Success(packet)
            } else {
                _eventDetailsState.value = UiState.Error("Associated club not found")
            }
        } else {
            _eventDetailsState.value = UiState.Error("Event not found")
        }
    }
}