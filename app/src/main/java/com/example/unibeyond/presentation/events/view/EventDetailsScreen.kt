package com.example.unibeyond.presentation.events.view

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.unibeyond.common.UiState
import com.example.unibeyond.presentation.events.EventDetailsStateData
import com.example.unibeyond.presentation.events.EventsViewModel

@Composable
fun EventDetailsScreen(
    navController: NavController,
    viewModel: EventsViewModel = hiltViewModel()){

    val uiState by viewModel.eventDetailsState.collectAsState()

    when (val state = uiState){
        is UiState.Loading->{
            CircularProgressIndicator()
        }
        is UiState.Error->{
            Text(state.message)
        }
        is UiState.Success->{
            val details = state.data
            EventDetailsScreenContent(
                club = details.club,
                event = details.event,
                isCurrentUserAttending = details.event.attendingUserIds.contains("u1"),
                onJoinClick = { viewModel.joinEvent(details.event.eventId)},
                onLeaveClick = { viewModel.leaveEvent(details.event.eventId) }
            )
        }
    }

}