package com.example.unibeyond.presentation.components.event_components

import androidx.compose.material.icons.outlined.Roofing
import androidx.compose.runtime.Composable
import com.example.unibeyond.domain.model.Event
import com.example.unibeyond.presentation.components.club_components.CategoryChip
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.People
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.unibeyond.R
import com.example.unibeyond.domain.model.Club
import com.example.unibeyond.domain.model.User
import com.example.unibeyond.presentation.components.generic_components.JoinedRibbon
import com.example.unibeyond.presentation.components.generic_components.UniBeyondButton
import com.example.unibeyond.ui.theme.UniBeyondGradient


@Composable
fun EventCardFull(
    event: Event,
    club: Club,
    isCurrentUserAttending: Boolean,
    onJoinClick: () -> Unit,
    onLeaveClick: () -> Unit,
    isJoined: Boolean
) {
    //A full card that shows details about the event
    Card(elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = colorResource(R.color.white)),
        modifier = Modifier.padding(18.dp)

    ) {
        //Box to hold image and joined ribbon so we can use align to place the icon
        Box(modifier = Modifier.fillMaxWidth()) {
            //Event image
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(event.imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = stringResource(R.string.club_image),
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            //Show the joined banner if the user is attending
            JoinedRibbon(
                isJoined = isJoined,
                modifier = Modifier
                    .align(Alignment.TopEnd)
            )
        }
        //Event name
        Text(
            text = event.eventName,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(start = 32.dp, top = 16.dp),
            fontWeight = FontWeight.Bold
        )

        //Category chip
        CategoryChip(
            category = event.category,
            modifier = Modifier.padding(start = 32.dp, bottom = 16.dp)
        )

        //Draw corresponding button depending on the joined status
        if (isCurrentUserAttending) {
            UniBeyondButton(
                text = stringResource(R.string.attending),
                onClick = onLeaveClick,
            )
        }
        else {
            UniBeyondButton(text = stringResource(R.string.attend), onClick = onJoinClick)
        }


        //Event description
        Text(
            text = stringResource(R.string.event_description),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 32.dp, top = 16.dp,end=32.dp)
        )
        Text(
            text = event.description,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(start = 32.dp,end=32.dp)
        )

        //Divider
        HorizontalDivider(
            modifier = Modifier
                .padding(top = 16.dp, start = 32.dp, end = 32.dp)
                .background(color = Gray)
        )

        //A container holding the attendees as a number and the club organizing the event
        Row(modifier = Modifier.padding(top = 16.dp, start = 32.dp, end = 8.dp)) {
            Icon(
                imageVector = Icons.Outlined.People,
                contentDescription = stringResource(R.string.attendees),
                modifier = Modifier
                    .padding(end = 8.dp, top = 8.dp)
                    .graphicsLayer(alpha = 0.99f)
                    .drawWithCache {
                        onDrawWithContent {
                            drawContent()
                            drawRect(
                                brush = UniBeyondGradient,
                                blendMode = BlendMode.SrcAtop
                            )
                        }
                    },
                tint = Color.Unspecified
            )

            //Column containing the text and number for the attendees
            Column() {
                Text(
                    text = stringResource(R.string.attendees),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium,


                    modifier = Modifier.padding()
                )
                Text(
                    text = event.attendingUserIds.size.toString(),
                    modifier = Modifier.padding()
                )
            }

            Icon(
                imageVector = Icons.Outlined.Roofing,
                contentDescription = stringResource(R.string.club_name),
                modifier = Modifier
                    .padding(end = 8.dp, top = 8.dp, start = 64.dp)
                    .graphicsLayer(alpha = 0.99f)
                    .drawWithCache {
                        onDrawWithContent {
                            drawContent()
                            drawRect(
                                brush = UniBeyondGradient,
                                blendMode = BlendMode.SrcAtop
                            )
                        }
                    },
                tint = Color.Unspecified
            )
            //Column containing info about which club is hosting
            Column(modifier = Modifier.padding(start = 8.dp, bottom = 18.dp)) {
                Text(
                    text = stringResource(R.string.club_name),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(text = club.name)
            }
        }
    }
}

