package com.example.unibeyond.presentation.clubs.manage

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unibeyond.common.UiState
import com.example.unibeyond.domain.model.Club
import com.example.unibeyond.domain.model.Event
import com.example.unibeyond.domain.model.Expense
import com.example.unibeyond.domain.model.User
import com.example.unibeyond.domain.repository.ClubRepository
import com.example.unibeyond.domain.repository.EventRepository
import com.example.unibeyond.domain.repository.UserRepository
import com.example.unibeyond.presentation.clubs.view.ClubDetailsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ManageClubState(
    val club: Club,
    val ownerName: String,
    val members: List<User>,
    val events: List<Event>,
    val pendingMembers:List<User>
)
@HiltViewModel
class ManageClubViewModel @Inject constructor(
    private val clubRepository: ClubRepository,
    private val userRepository: UserRepository,
    private val eventRepository: EventRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val clubId: String = checkNotNull(savedStateHandle["clubId"])

    private val _uiState = MutableStateFlow<UiState<ManageClubState>>(UiState.Loading)
    val uiState: StateFlow<UiState<ManageClubState>> = _uiState.asStateFlow()

    //----Mutable States for the ui-----
    var nameInput by mutableStateOf("")
    var descriptionInput by mutableStateOf("")
    var imageUrlInput by mutableStateOf("")
    var categoryInput by mutableStateOf("")


    init{
        loadClub(clubId)
    }

    //Get current data from club
    private fun loadClub(id: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading

            val club = clubRepository.getClubById(id) // get club by the id we got from navigation

            if (club != null) {

                nameInput = club.name
                descriptionInput = club.description
                imageUrlInput = club.imageUrl
                categoryInput = club.category

                val owner = userRepository.getUser(club.ownerId).firstOrNull() //get the owner of the club
                val ownerName = owner?.fullName ?: "Unknown" //get the name of the owner




                //Get a list of the type User where every member of the list is a member of the club
                val members : List<User> = club.memberIds.mapNotNull { userId ->
                    userRepository.getUser(userId).firstOrNull()
                }

                //Get a list of the type Event where for every event the clubId is the same as the club we got from navigation
                val events : List<Event> = eventRepository.getEventsForClub(id).firstOrNull() ?: emptyList()


                //Get a list of type User for every user in list pending members
                val pendingMembers : List<User> = club.pendingMemberIds.mapNotNull { userId ->
                    userRepository.getUser(userId).firstOrNull()
                }
                //Update uiState value to be shown in ui
                _uiState.value = UiState.Success(
                    ManageClubState(
                        club = club,
                        ownerName = ownerName,
                        members = members,
                        events = events,
                        pendingMembers
                    )
                )

            } else {
                _uiState.value = UiState.Error("Club not found")
            }
        }
    }
    //Accept member
    fun acceptMember(userId: String) {
     viewModelScope.launch {
         //Switch ui state to loading
         _uiState.value = UiState.Loading
         //Use repository to accept member
         val result = clubRepository.acceptMember(clubId, userId)

         //Check if its successful
         if(result.isSuccess){
             loadClub(clubId)
         }
         else {
             _uiState.value = UiState.Error("Failed to accept member")

         }

     }
    }

    //Decline member
    fun declineMember(userId: String) {
        viewModelScope.launch {
            //Switch ui state to loading
            _uiState.value = UiState.Loading
            //Use repository to decline member
            val result = clubRepository.declineMember(clubId, userId)
            //Check if its successful
            if (result.isSuccess) {
                loadClub(clubId)
            } else {
                _uiState.value = UiState.Error("Failed to decline member")
            }
        }
    }

    //Remove member
    fun removeMember(userId: String) {
        viewModelScope.launch {
            //Switch ui state to loading
            _uiState.value = UiState.Loading
            //Use repository to remove member
            val result = clubRepository.removeMember(clubId, userId)
            //Check if its successful
            if (result.isSuccess) {
                loadClub(clubId)
            } else {
                _uiState.value = UiState.Error("Failed to remove member")
            }
        }
    }

    //Add expense
    fun addExpense(expense: Expense) {
        viewModelScope.launch {
            //Switch ui state to loading
            _uiState.value = UiState.Loading
            //Use repository to add expense
            val result = clubRepository.addExpense(clubId, expense)
            //Check if its successful
            if (result.isSuccess) {
                loadClub(clubId)
            } else {
                _uiState.value = UiState.Error("Failed to add expense")
            }
        }
    }

    //Update budget
    fun updateBudget(budget: Double) {
        viewModelScope.launch {
            //Switch ui state to loading
            _uiState.value = UiState.Loading
            //Use repository to update budget
            val result = clubRepository.updateBudget(clubId, budget)
            //Check if its successful
            if (result.isSuccess) {
                loadClub(clubId)
            } else {
                _uiState.value = UiState.Error("Failed to update budget")
            }
        }
    }

    //Add budget
    fun addBudget(amount: Double) {
        viewModelScope.launch {
            //Switch ui state to loading
            _uiState.value = UiState.Loading
            //Use repository to add budget
            val result = clubRepository.addBudget(clubId, amount)
            //Check if its successful
            if (result.isSuccess) {
                loadClub(clubId)
            } else {
                _uiState.value = UiState.Error("Failed to add budget")
            }
        }
    }

    //Update club details
    fun updateClub(club: Club) {
        viewModelScope.launch {
            //Switch ui state to loading
            _uiState.value = UiState.Loading
            //Use repository to update club
            val result = clubRepository.updateClub(club)
            //Check if its successful
            if (result.isSuccess) {
                loadClub(clubId)
            } else {
                _uiState.value = UiState.Error("Failed to update club")
            }
        }
    }


    //Save general info
    fun onSaveGeneralInfo() {
        val state = _uiState.value
        if (state is UiState.Success) {
            //Take old object and copy it to a new object with changed info
            val updatedClub = state.data.club.copy(
                name = nameInput,
                description = descriptionInput,
                imageUrl = imageUrlInput,
                category = categoryInput
            )
            updateClub(updatedClub) // call the updateCLub
        }
    }
}
