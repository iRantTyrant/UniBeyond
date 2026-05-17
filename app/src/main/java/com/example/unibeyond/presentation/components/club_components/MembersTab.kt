package com.example.unibeyond.presentation.components.club_components

import android.provider.MediaStore
import com.example.unibeyond.R
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.unibeyond.domain.model.User
import com.example.unibeyond.presentation.clubs.manage.ManageClubState
import com.example.unibeyond.presentation.clubs.manage.ManageClubViewModel
import com.example.unibeyond.presentation.components.generic_components.BackButton
import com.example.unibeyond.presentation.components.generic_components.UserIconAndName

@Composable
fun MembersTab(
    state: ManageClubState,
    viewModel: ManageClubViewModel,
    onBackClick: () -> Unit
){
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            BackButton(onClick = onBackClick)
        }
        //Pending Members section
        item{
            Column(modifier = Modifier) {
                Card(
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "${stringResource(R.string.pending_members)} (${state.pendingMembers.size})",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(16.dp)
                    )

                    //For every pending member we get a row
                    for (pendingMember in state.pendingMembers) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            //First the composable for name and icon
                            Box(modifier = Modifier.weight(1f)) {
                                UserIconAndName(
                                    members = listOf(pendingMember),
                                    ownerName = state.ownerName
                                )
                            }


                            //Accept member
                            IconButton(onClick = { viewModel.acceptMember(pendingMember.userId) }) {
                                Icon(
                                    painter = painterResource(R.drawable.outline_check_24),
                                    contentDescription = stringResource(R.string.accept_member),
                                    tint = Color.Green
                                )
                            }

                            //Decline member
                            IconButton(onClick = { viewModel.declineMember(pendingMember.userId) }) {
                                Icon(
                                    painter = painterResource(R.drawable.outline_delete_24),
                                    contentDescription = stringResource(R.string.decline_member),
                                    tint = Color.Red,
                                    modifier = Modifier.padding(end = 16.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

        // Joined Members Section
        item{
            Card(elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                modifier = Modifier.fillMaxWidth()){
                Column(
                    modifier = Modifier.padding(16.dp)
                ){
                    Text(text ="${stringResource(R.string.members)} (${state.members.size})",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(bottom = 16.dp))

                    //For every member we make a different row
                    for(member in state.members){
                        Row(modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically ){
                            //First the composable for name and icon
                            Box(modifier = Modifier.weight(1f)){
                                UserIconAndName(
                                    members = listOf(member),
                                    ownerName = state.ownerName
                                )
                            }

                            //Then the button icon
                            if (member.userId != state.club.ownerId){// Check if the user id is the same as the owners to not show the button
                                IconButton(onClick = { viewModel.removeMember(member.userId)}){
                                    Icon(
                                        painter = painterResource(R.drawable.outline_person_remove_24),
                                        contentDescription = stringResource(R.string.remove_member),
                                        tint = Color.Gray
                                    )
                                }
                            }
                        }
                    }
                }

            }
        }
    }
}