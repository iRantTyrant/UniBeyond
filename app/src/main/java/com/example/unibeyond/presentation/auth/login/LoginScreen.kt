package com.example.unibeyond.presentation.auth.login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.unibeyond.R
import com.example.unibeyond.presentation.components.auth_components.AuthLayout
import com.example.unibeyond.presentation.components.generic_components.UniBeyondTextField
import com.example.unibeyond.presentation.navigation.Screen

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    // Get ui state
    val state by viewModel.loginUiState.collectAsState()

    //We use this to close the keyboard
    val focusManager = LocalFocusManager.current

    //Navigate to discover if login success
    LaunchedEffect(key1 = state.isSuccess){
        if (state.isSuccess){
            navController.navigate(Screen.Discover.route){
                popUpTo(Screen.Login.route){
                    inclusive = true
                }
            }
        }
    }

    AuthLayout(
        title = stringResource(id = R.string.welcome_back),
        subtitle = stringResource(R.string.login_subtitle),
        buttonLabel = stringResource(R.string.login_button),

        //Close keyboard and login
        buttonFunc = {
            focusManager.clearFocus()
            viewModel.onLoginClick()
        },

        //Loading
        isLoading = state.isLoading
    ) {

        // --- EMAIL FIELD ---
        UniBeyondTextField(
            value = state.email, // Δεν χρειάζεται .value επειδή βάλαμε 'by'
            onValueChange = { viewModel.onEmailChange(it) },
            label = stringResource(id = R.string.email),
            placeholder = stringResource(id = R.string.email_placeholder),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            visualTransformation = VisualTransformation.None,
            trailingIcon = null
        )

        Spacer(modifier = Modifier.height(16.dp)) // Λίγο κενό

        // --- PASSWORD FIELD ---
        UniBeyondTextField(
            value = state.password,
            onValueChange = { viewModel.onPasswordChange(it) },
            label = stringResource(id = R.string.password),
            placeholder = stringResource(id = R.string.password_placeholder),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),

            //Password visibility
            visualTransformation = if (state.isPasswordVisible) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            //Trailin Icon with button to toggle password visibillity
            trailingIcon = {
                val iconImage = if (state.isPasswordVisible) {
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

       //Error message for the user
        if (state.error != null) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = state.error!!,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // --- REGISTER LINK ---
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
                onClick = { navController.navigate(Screen.Register.route) },
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