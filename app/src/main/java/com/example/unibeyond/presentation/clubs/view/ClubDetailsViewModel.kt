package com.example.unibeyond.presentation.clubs.view

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unibeyond.common.UiState
import com.example.unibeyond.domain.model.Club
import com.example.unibeyond.domain.model.Event
import com.example.unibeyond.domain.model.User
import com.example.unibeyond.domain.repository.ClubRepository
import com.example.unibeyond.domain.repository.EventRepository
import com.example.unibeyond.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.coroutines.flow.firstOrNull

//What the viewmodel will send to the ui
data class ClubDetailsState(
    val club: Club ,
    val ownerName: String ,
    val isCurrentUserMember: Boolean,
    val members : List<User>,
    val events : List<Event>
)
@HiltViewModel
class ClubDetailsViewModel @Inject constructor(
    private val clubRepository: ClubRepository,
    private val userRepository: UserRepository,
    private val eventRepository: EventRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val currentUserId="u1"
    //We make a private val to store the _uiState safe and be able to change it only in this file. we use the uiState to get info from the _uiState

    private val _uiState = MutableStateFlow<UiState<ClubDetailsState>>(UiState.Loading)
    val uiState: StateFlow<UiState<ClubDetailsState>> = _uiState.asStateFlow()

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
                val owner = userRepository.getUser(club.ownerId).firstOrNull() //get the owner of the club
                val ownerName = owner?.fullName ?: "Unknown" //get the name of the owner
                val isCurrentUserMember = club.memberIds.contains(currentUserId) //Check if the current user is a member of the club


                //Get a list of the type User where every member of the list is a member of the club
                val members : List<User> = club.memberIds.mapNotNull { userId ->
                    userRepository.getUser(userId).firstOrNull()
                }

                //Get a list of the type Event where for every event the clubId is the same as the club we got from navigation
                val events : List<Event> = eventRepository.getEventsForClub(id).firstOrNull() ?: emptyList()


                _uiState.value = UiState.Success(
                    ClubDetailsState(
                        club = club,
                        ownerName = ownerName,
                        isCurrentUserMember = isCurrentUserMember,
                        members = members,
                        events = events
                    )
                )

            } else {
                _uiState.value = UiState.Error("Club not found")
            }
        }
    }
}