package com.example.unibeyond.presentation.components.club_components
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import coil.request.ImageRequest
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.material.icons.filled.Group
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.unibeyond.R
import com.example.unibeyond.domain.model.Club
import com.example.unibeyond.presentation.components.generic_components.JoinedRibbon

//A small ClubCard to be shown in the discover screen with minimal info
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClubCard(
    club: Club,
    onClubClick: () -> Unit,
    isJoined: Boolean
) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = colorResource(R.color.white)),
        onClick = onClubClick,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column {
            //iMAGE BOX
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .background(Color.LightGray) //when the image isnt loaded it shows up as gray
            ) {
                // Using coil we load the image
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(club.imageUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = stringResource(R.string.club_image),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                // Show the joined banner if the user is a member
                JoinedRibbon(
                    isJoined = isJoined,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                )
            }

            // Information about the club
            Column(modifier = Modifier.padding(16.dp)) {

                // Title
                Text(
                    text = club.name,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )

                // Category
                CategoryChip(category = club.category)


                Spacer(modifier = Modifier.height(8.dp))

                // Small description (max lines 2)
                Text(
                    text = club.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )

                Spacer(modifier = Modifier.height(12.dp))

                // The member count
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Group,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))

                    //Count all ids in the list
                    Text(
                        text = "${club.memberIds.size} ${stringResource(R.string.members)}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}