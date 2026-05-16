package com.example.unibeyond.presentation.clubs.manage

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.unibeyond.common.UiState

@Composable
fun ClubManageScreen(
    navController: NavController,
    //We inject the viewModel here through hilt
    viewModel: ManageClubViewModel = hiltViewModel()
) {
    // This is where we look at the uistate
    val uiState by viewModel.uiState.collectAsState()

    // Loading, Success, Error)
    Box(modifier = Modifier) {
        when (val state = uiState) {
            is UiState.Loading -> {

            }
            is UiState.Success -> {
                // We call the content and give it the view model and nav controller
                ClubManageScreenContent(
                    onBackClick = { navController.popBackStack() },
                    viewModel = viewModel,

                    // state = state.data
                    )
                }
                is UiState.Error -> {
                    // Show error
                }
            }
        }
    }

