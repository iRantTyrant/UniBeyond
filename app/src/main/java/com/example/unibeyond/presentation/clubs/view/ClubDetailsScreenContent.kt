package com.example.unibeyond.presentation.clubs.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.unibeyond.R
import com.example.unibeyond.domain.model.Club
import com.example.unibeyond.domain.model.Event
import com.example.unibeyond.domain.model.User
import com.example.unibeyond.presentation.components.expense_component.BudgetSummaryBox
import com.example.unibeyond.presentation.components.club_components.ClubCardFull
import com.example.unibeyond.presentation.components.event_components.EventsCard
import com.example.unibeyond.presentation.components.expense_component.ExpenseCard
import com.example.unibeyond.presentation.components.expense_component.ExpenseItem
import com.example.unibeyond.presentation.components.generic_components.BackButton
import com.example.unibeyond.presentation.components.generic_components.UniBeyondButton


//The Screen that shows the Club Details
@Composable
fun ClubDetailsScreenContent(club: Club,
                             ownerName: String,
                             isCurrentUserMember: Boolean,
                             currentUserId : String,
                             members:List<User>,
                             events:List<Event>,
                             onBackClick: () -> Unit,
                             onJoinClick: () -> Unit,
                             onLeaveClick: () -> Unit,
                             onManageClick: () -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        //Back button
        item {
            BackButton(onClick = onBackClick)
        }
        //Main info card
        item {
            ClubCardFull(
                club = club,
                ownerName = ownerName,
                isCurrentUserMember = isCurrentUserMember,
                currentUserId = currentUserId,
                members = members,
                onJoinClick = onJoinClick,
                onLeaveClick = onLeaveClick,
                isJoined = isCurrentUserMember
            )
        }
        //Events card
        item {
            EventsCard(events)
        }
        //Budget card
        item {
            if (isCurrentUserMember) {
                ExpenseCard(
                    club = club
                )
            }
        }
        //Button that redirects the user if they are the owner to the manage club screen
        item {
            if (club.ownerId == currentUserId) {
                UniBeyondButton(
                    text = stringResource(R.string.manage_club),
                    onClick = { onManageClick() }
                )
            }
        }
    }
}



