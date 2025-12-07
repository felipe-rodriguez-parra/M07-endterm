package com.example.spaceapps.ui.pages


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.spaceapps.R // Necesitarás este archivo en tu proyecto
import com.example.spaceapps.ui.theme.SpaceAppsTheme // Tu tema de Compose
import android.content.Intent
import android.net.Uri
import android.util.Patterns
import kotlinx.coroutines.launch

/**
 * Pantalla de Login con validación local y Snackbar para errores.
 * @param onLoginSuccess Función de callback para navegar a la Home al iniciar sesión con éxito.
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

    fun validateAndLogin() {
        emailError = if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            context.getString(R.string.error_invalid_email)
        } else null

        passwordError = if (password.length < 8) { // Mínimo de 8 caracteres
            context.getString(R.string.error_short_password)
        } else null

        if (emailError == null && passwordError == null) {
            if (email == validEmail && password == validPassword) {
                onLoginSuccess()
            } else {
                // Muestra Snackbar para credenciales incorrectas
                val message = context.getString(R.string.error_invalid_credentials)

                // 3. CORRECCIÓN: Usar scope.launch{} en lugar de LaunchedEffect{}
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

    // Función para abrir la URL de contacto
    fun openForgotPasswordUrl() {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://lasallefp.com/contactar/"))
        context.startActivity(intent)
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(R.string.login_title),
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 48.dp)
            )

            // Campo de Email
            OutlinedTextField(
                value = email,
                onValueChange = { email = it; emailError = null },
                label = { Text(stringResource(R.string.field_email)) },
                leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
                isError = emailError != null,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
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

            // Campo de Contraseña
            OutlinedTextField(
                value = password,
                onValueChange = { password = it; passwordError = null },
                label = { Text(stringResource(R.string.field_password)) },
                leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
                isError = passwordError != null,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
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

            // Botón Olvidé mis datos
            TextButton(onClick = ::openForgotPasswordUrl) {
                Text(stringResource(R.string.button_forgot_password))
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Botón Iniciar Sesión
            Button(
                onClick = ::validateAndLogin,
                modifier = Modifier.fillMaxWidth().height(56.dp)
            ) {
                Text(stringResource(R.string.button_login))
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