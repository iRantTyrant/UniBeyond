package com.example.unibeyond.presentation.components.generic_components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.unibeyond.R


//A ribbon that shows if the user is a member of the club
@Composable
fun JoinedRibbon(
    isJoined: Boolean,
    modifier: Modifier = Modifier
) {
    if (isJoined) {
        Surface(
            color = Color(0xFF00C853),
            shape = RoundedCornerShape(bottomStart = 8.dp),
            modifier = modifier
        ) {
            Text(
                text = stringResource(R.string.joined),
                color = Color.White,
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
            )
        }
    }
}
