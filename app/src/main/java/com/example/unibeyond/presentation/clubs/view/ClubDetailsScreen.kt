package com.example.unibeyond.presentation.clubs.view

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.unibeyond.common.UiState


@Composable
fun ClubDetailsScreen(navController: NavController, viewModel: ClubDetailsViewModel = hiltViewModel()) {
    //Get uiState from the ViewModel
    val uiState by viewModel.uiState.collectAsState()

    Box() {
        when (val state = uiState) {
            is UiState.Loading -> {
                // Show loading state
                CircularProgressIndicator()
            }
            is UiState.Error -> {
                // Show error state
                Text(text = state.message, color = MaterialTheme.colorScheme.error, modifier = Modifier.align(Alignment.Center))
            }
            is UiState.Success -> {
                val detailsState = state.data

                // Show success state
                ClubDetailsScreenContent(club = detailsState.club ,
                    ownerName = detailsState.ownerName,
                    isCurrentUserMember = detailsState.isCurrentUserMember,
                    currentUserId = detailsState.currentUserId,
                    members = detailsState.members,
                    events = detailsState.events,
                    onBackClick = { navController.popBackStack() },
                    onJoinClick = { viewModel.joinClub() },
                    onLeaveClick = { viewModel.leaveClub()},
                    onManageClick = { navController.navigate("manage_club_screen/${detailsState.club.id}")}
                    )
            }


        }
    }

}