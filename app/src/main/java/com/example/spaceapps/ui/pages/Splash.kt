package com.example.spaceapps.ui.pages


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.spaceapps.R // Necesitarás este archivo en tu proyecto
import com.example.spaceapps.ui.theme.SpaceAppsTheme // Tu tema de Compose

/**
 * Pantalla de Splash. Muestra un título y navega automáticamente.
 * @param onTimeout función que se llama después del tiempo de espera de 2 segundos.
 */
@Composable
fun Splash(onTimeout: () -> Unit) {
    // Ejecuta una función después de 2 segundos
    LaunchedEffect(Unit) {
        kotlinx.coroutines.delay(1500) // 2000ms = 2 segundos
        onTimeout()
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background // Usa el color de fondo del tema
    ) {
        Box(
            modifier = Modifier.fillMaxSize().padding(32.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                // Placeholder para el Logo (opcional)
                /*
                Image(
                    painter = painterResource(id = R.drawable.app_logo),
                    contentDescription = stringResource(R.string.app_name),
                    modifier = Modifier.size(128.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                */

                Text(
                    text = stringResource(R.string.app_name), // "SpaceApps"
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSplash() {
    SpaceAppsTheme {
        Splash(onTimeout = {})
    }
}