package com.example.unibeyond.presentation.clubs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.unibeyond.common.UiState
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.res.stringResource
import com.example.unibeyond.R
import com.example.unibeyond.presentation.components.ClubCard


@Composable
fun DiscoverScreen(
    navController: NavController,
    viewModel: ClubsViewModel = hiltViewModel()
) {
    // Grab the ui state from the ViewModel
    val uiState = viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Header
        Text(
            text = stringResource(R.string.discover_clubs),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = stringResource(R.string.discover_subtitle),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(bottom = 16.dp)
        )


        // Check what State the ui is
        when (val state = uiState.value) {

            // If Loading
            is UiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            // If error
            is UiState.Error -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        text = state.message,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }

            // If Success Create the list
            is UiState.Success -> {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(bottom = 80.dp)
                ) {
                    // Grab the list of clubs from the state
                    items(state.data) { club ->
                        //Check if user is joined u1 is an example
                        val isJoined = club.memberIds.contains("u1")

                        //Show Cards
                        ClubCard(
                            club = club,
                            isJoined = isJoined,
                            onClubClick = {navController.navigate("club_details_screen/${club.id}")},


                        )
                    }
                }
            }
        }
    }
}