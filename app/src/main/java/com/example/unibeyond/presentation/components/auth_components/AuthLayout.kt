package com.example.unibeyond.presentation.components.auth_components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.unibeyond.presentation.components.generic_components.UniBeyondButton

//A layout for the auth screens that uses the same header footers and backgrounds
@Composable
fun AuthLayout(
    //Different title - subtitle for each form
    title: String,
    subtitle : String,
    buttonLabel : String,
    buttonFunc : () -> Unit,
    isLoading:Boolean = false,
    //The content of the form its a composable function that returns nothing
    content : @Composable () -> Unit
){
    Scaffold(
        modifier = Modifier.fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surfaceContainerLow)
    ){padding ->
        Column(
            modifier = Modifier.padding(padding).fillMaxSize()
                .background(color = MaterialTheme.colorScheme.surfaceBright)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            //First the header
            LoginRegisterHeader()

            //Small space between the header and the content
            Spacer(modifier = Modifier.weight(1f))

            //The space for each form
            Card(
                modifier = Modifier.padding(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
            ){
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Text(title,
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(16.dp)
                    )
                    Text(text= subtitle,
                        color = MaterialTheme.colorScheme.secondary,
                        style = MaterialTheme.typography.bodyMedium)
                    content()// Here is the form
                    UniBeyondButton(buttonLabel, buttonFunc)
                }
            }

            //Small space between the form and the footer
            Spacer(modifier = Modifier.weight(1f))

            //The footer
            LoginRegisterFooter()
        }
    }
}