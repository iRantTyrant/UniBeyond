package com.example.unibeyond.presentation.auth.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
fun RegisterScreen(viewModel : RegisterViewModel = hiltViewModel(),
                   navController: NavController){
    val state = viewModel.registerUiState.collectAsState()

    //NAvigate to login if success
    LaunchedEffect(key1 = state.value.isSuccess) {
        if (state.value.isSuccess) {
            navController.navigate(Screen.Login.route) {
                popUpTo(Screen.Register.route) {
                    inclusive = true
                }
            }
        }
    }

    AuthLayout(title = "Register",
        subtitle = stringResource(R.string.register_subtitle),
        buttonLabel = stringResource(R.string.sign_up),
        buttonFunc = {viewModel.onRegisterClick()}){


        //The Full name field
        UniBeyondTextField(
            value = state.value.fullName,
            onValueChange = {viewModel.onFullNameChange(it)},
            label = stringResource(R.string.full_name),
            placeholder = stringResource(R.string.placeholders_full_name),
        )

        //The major field
        UniBeyondTextField(
            value = state.value.major,
            onValueChange = {viewModel.onMajorChange(it)},
            label = stringResource(R.string.major),
            placeholder = stringResource(R.string.placeholders_major),
        )

        //The year field
        UniBeyondTextField(
            value = state.value.year,
            onValueChange = {viewModel.onYearChange(it)},
            label = stringResource(R.string.year),
            placeholder = stringResource(R.string.placeholders_year),
        )

        //Email Field
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

                //The visibility for the password
                IconButton(onClick = { viewModel.onTogglePasswordVisibility() }){
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
                text = stringResource(R.string.already_have_account),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )


            TextButton(
                onClick = {
                    navController.navigate(Screen.Login.route)
                },

                contentPadding = PaddingValues(0.dp)
            ) {
                Text(
                    text = stringResource(R.string.login_button),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }

    }

}