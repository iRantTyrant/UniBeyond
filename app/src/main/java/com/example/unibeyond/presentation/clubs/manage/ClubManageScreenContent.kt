package com.example.unibeyond.presentation.clubs.manage

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.unibeyond.R
import com.example.unibeyond.presentation.components.club_components.InfoTab
import com.example.unibeyond.presentation.components.generic_components.BackButton
import com.example.unibeyond.presentation.components.generic_components.UniTabRow

@Composable
fun ClubManageScreenContent(
    viewModel: ManageClubViewModel,
    onBackClick: () -> Unit,
    ){

    val titles = listOf(stringResource(R.string.details), stringResource(R.string.budget), stringResource(R.string.members), stringResource(R.string.events))
    var currentTab by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier.fillMaxSize()
    ){
        UniTabRow(
            selectedTabIndex = currentTab,
            onTabSelected = { index -> currentTab = index},
            titles = titles
        )


        when (currentTab) {
            0 -> InfoTab(
                viewModel  = viewModel,
                onBackClick = onBackClick
            )

            1 -> Text(text = "Events")
            2 -> Text(text = "Budget")
            3 -> Text(text = "eVENTSSSS")


        }
        }
    }



