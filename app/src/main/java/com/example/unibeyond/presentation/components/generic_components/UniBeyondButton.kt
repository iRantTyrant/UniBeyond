package com.example.unibeyond.presentation.components.generic_components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.unibeyond.ui.theme.UniBeyondGradient

@Composable
fun UniBeyondButton(text: String, onClick: () -> Unit){
    val shape = RoundedCornerShape(8.dp)
    Button(
        onClick = onClick,
        modifier = Modifier
            .padding(start = 20.dp, end = 20.dp, bottom = 10.dp, top = 10.dp)
            .fillMaxWidth()
            .height(50.dp)
            .background(brush = UniBeyondGradient, shape = shape),
        shape = shape,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.onPrimary
        )
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}
