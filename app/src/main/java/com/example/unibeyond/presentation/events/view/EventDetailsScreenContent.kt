package com.example.unibeyond.presentation.events.view

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.unibeyond.common.UiState
import com.example.unibeyond.domain.model.Club
import com.example.unibeyond.domain.model.Event
import com.example.unibeyond.presentation.components.event_components.EventCardFull

@Composable
fun EventDetailsScreenContent(club : Club,event: Event,
                              isCurrentUserAttending: Boolean,
                              onJoinClick: ()->Unit,
                              onLeaveClick:()->Unit){
    LazyColumn {
        item {
            EventCardFull(
                event = event,
                club = club,
                isCurrentUserAttending = isCurrentUserAttending,
                onJoinClick = onJoinClick,
                onLeaveClick = onLeaveClick,
                isJoined = isCurrentUserAttending
            )
        }
        item{
            Card(){
                LazyColumn(Modifier.size(400.dp)) {

                }
            }
        }
    }
}