package com.example.unibeyond.presentation.components.generic_components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.unibeyond.R
import com.example.unibeyond.domain.model.User
import com.example.unibeyond.ui.theme.UniBeyondGradient


//A composable component that shows the user avatar and name in a row with a star icon if the user is the owner of the club
@Composable
fun UserIconAndName(members: List<User>, ownerName: String) {
    //A container showing all the members and their avatar
    Column(modifier = Modifier.padding(top = 16.dp, start = 32.dp, bottom = 32.dp)) {
        for (member in members) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                //The avatar
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(member.profileImageUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = stringResource(R.string.user_avatar),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(60.dp)
                        .padding(8.dp)
                        .clip(
                            RoundedCornerShape(40.dp)
                        )
                )
                if (member.fullName === ownerName) {
                    Text(
                        text = member.fullName,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                    Icon(
                        imageVector = Icons.Filled.Star,
                        contentDescription = null,
                        modifier = Modifier.padding(start = 16.dp)
                            .graphicsLayer(alpha = 0.99f)
                            .drawWithCache {
                                onDrawWithContent {
                                    drawContent()
                                    // Draw the gradient on top of the icon
                                    drawRect(
                                        brush = UniBeyondGradient, // The gradient
                                        blendMode = BlendMode.SrcAtop // Blend the icon with the gradient

                                    )
                                }
                            },
                        tint = Color.Unspecified // remove the default tint
                    )
                } else {
                    Text(
                        text = member.fullName,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
            }
        }
    }
}