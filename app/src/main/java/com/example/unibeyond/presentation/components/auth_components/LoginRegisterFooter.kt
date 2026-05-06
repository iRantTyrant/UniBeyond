package com.example.unibeyond.presentation.components.auth_components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.unibeyond.R


data class FooterItem(
    val icon: Int,
    val text: Int
)

@Composable
fun LoginRegisterFooter(modifier: Modifier = Modifier) {

    //We keep the icons and labels in the same list
    val items = listOf(
        Pair(R.drawable.discover, R.string.discover),
        Pair(R.drawable.location, R.string.location),
        Pair(R.drawable.connect, R.string.connect)
    )

    //We make a row for the footer
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        //And for every item in the list we make a column for the icon and the text
        items.forEach { item ->

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(8.dp)
            ) {
                Icon(
                    painter = painterResource(id = item.first),
                    contentDescription = null,
                    modifier = Modifier.size(48.dp),
                    tint = Color.Unspecified
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = stringResource(id = item.second),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginRegisterFooterPreview() {
    LoginRegisterFooter()
}