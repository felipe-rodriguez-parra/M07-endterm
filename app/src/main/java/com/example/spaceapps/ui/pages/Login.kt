package com.example.spaceapps.ui.pages

import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.example.spaceapps.R
import com.example.spaceapps.ui.theme.SpaceAppsTheme
import com.example.spaceapps.ui.theme.SpaceBackground
import com.example.spaceapps.ui.theme.SpaceColors
import com.example.spaceapps.ui.theme.SpaceTheme
import kotlinx.coroutines.launch

/**
 * Login screen with local validation and Snackbar for errors.
 * @param onLoginSuccess Callback function to navigate to the Home on successful login.
 */
@Composable
fun Login(onLoginSuccess: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }

    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    val scope = rememberCoroutineScope()

    val validEmail = "admin@lasalle.es"
    val validPassword = "admin1234"

    val colors = SpaceTheme.colors

    fun validateAndLogin() {
        emailError = if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            context.getString(R.string.error_invalid_email)
        } else null

        passwordError = if (password.length < 8) {
            context.getString(R.string.error_short_password)
        } else null

        if (emailError == null && passwordError == null) {
            if (email == validEmail && password == validPassword) {
                onLoginSuccess()
            } else {
                val message = context.getString(R.string.error_invalid_credentials)
                scope.launch {
                    snackbarHostState.showSnackbar(
                        message = message,
                        actionLabel = context.getString(R.string.action_retry),
                        duration = SnackbarDuration.Short
                    )
                }
            }
        }
    }

    fun openForgotPasswordUrl() {
        val intent = Intent(Intent.ACTION_VIEW, "https://lasallefp.com/contactar/".toUri())
        context.startActivity(intent)
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        modifier = Modifier.fillMaxSize(),
        containerColor = Color.Transparent
    ) { paddingValues ->
        SpaceBackground(showDecorativeCircles = true) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Title
                Row {
                    Text(
                        text = "Space",
                        style = MaterialTheme.typography.headlineLarge,
                        color = colors.textPrimary
                    )
                    Text(
                        text = "X",
                        style = MaterialTheme.typography.headlineLarge,
                        color = SpaceColors.PrimaryLight
                    )
                }
                Text(
                    text = stringResource(R.string.login_title),
                    style = MaterialTheme.typography.titleMedium,
                    color = colors.textMuted,
                    modifier = Modifier.padding(bottom = 48.dp)
                )

                // Email field
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it; emailError = null },
                    label = { Text(stringResource(R.string.field_email), color = colors.textMuted) },
                    leadingIcon = { Icon(Icons.Default.Email, contentDescription = null, tint = colors.textMuted) },
                    isError = emailError != null,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = colors.surfaceOverlay,
                        unfocusedContainerColor = colors.surfaceOverlay,
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = Color.Transparent,
                        focusedTextColor = colors.textPrimary,
                        unfocusedTextColor = colors.textPrimary,
                        cursorColor = MaterialTheme.colorScheme.primary,
                        focusedLabelColor = SpaceColors.PrimaryLight,
                        unfocusedLabelColor = colors.textMuted
                    )
                )
                if (emailError != null) {
                    Text(
                        text = emailError!!,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.align(Alignment.Start).padding(start = 16.dp, bottom = 16.dp)
                    )
                } else {
                    Spacer(modifier = Modifier.height(16.dp))
                }

                // Password field
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it; passwordError = null },
                    label = { Text(stringResource(R.string.field_password), color = colors.textMuted) },
                    leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = colors.textMuted) },
                    isError = passwordError != null,
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = colors.surfaceOverlay,
                        unfocusedContainerColor = colors.surfaceOverlay,
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = Color.Transparent,
                        focusedTextColor = colors.textPrimary,
                        unfocusedTextColor = colors.textPrimary,
                        cursorColor = MaterialTheme.colorScheme.primary,
                        focusedLabelColor = SpaceColors.PrimaryLight,
                        unfocusedLabelColor = colors.textMuted
                    )
                )
                if (passwordError != null) {
                    Text(
                        text = passwordError!!,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.align(Alignment.Start).padding(start = 16.dp, bottom = 16.dp)
                    )
                } else {
                    Spacer(modifier = Modifier.height(16.dp))
                }

                // Forgot password button
                TextButton(onClick = ::openForgotPasswordUrl) {
                    Text(stringResource(R.string.button_forgot_password), color = SpaceColors.PrimaryLight)
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Login button
                Button(
                    onClick = ::validateAndLogin,
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(stringResource(R.string.button_login))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLogin() {
    SpaceAppsTheme {
        Login(onLoginSuccess = {})
    }
}