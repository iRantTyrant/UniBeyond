package com.example.unibeyond.presentation.auth.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.unibeyond.R
import com.example.unibeyond.presentation.components.AuthLayout
import com.example.unibeyond.presentation.components.UniBeyondTextField
import com.example.unibeyond.presentation.navigation.Screen


@Composable
fun LoginScreen(
    viewModel : LoginViewModel = viewModel(),
    navController: NavController

){
    //For navigation between login and register



    val state = viewModel.loginUiState.collectAsState()

    AuthLayout(title = stringResource(id = R.string.welcome_back),
        subtitle = stringResource(R.string.login_subtitle),
        buttonLabel = stringResource(R.string.login_button),
        buttonFunc = {}){

        UniBeyondTextField(
            value = state.value.email,
            onValueChange = {viewModel.onEmailChange(it)},
            label = stringResource(id = R.string.email),
            placeholder = stringResource(id = R.string.email_placeholder),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            visualTransformation = VisualTransformation.None,
            trailingIcon = null
        )

        UniBeyondTextField(
            value = state.value.password,
            onValueChange = {viewModel.onPasswordChange(it)},
            label = stringResource(id = R.string.password),
            placeholder = stringResource(id = R.string.password_placeholder),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = if (state.value.isPasswordVisible){
                VisualTransformation.None
            }else{
                PasswordVisualTransformation()
            },
            trailingIcon = {

                val iconImage = if (state.value.isPasswordVisible) {
                    Icons.Filled.Visibility
                } else {
                    Icons.Filled.VisibilityOff
                }


                IconButton(onClick = { viewModel.onTogglePasswordVisibility() }) {
                    Icon(
                        imageVector = iconImage,
                        contentDescription = "Toggle password visibility",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = stringResource(R.string.no_account),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )


            TextButton(
                onClick = {
                    navController.navigate(Screen.Register.route)
                },

                contentPadding = PaddingValues(0.dp)
            ) {
                Text(
                    text = stringResource(R.string.sign_up),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }


}