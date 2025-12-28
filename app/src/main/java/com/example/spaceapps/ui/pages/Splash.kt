package com.example.spaceapps.ui.pages


import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.spaceapps.R
import com.example.spaceapps.ui.theme.SpaceAppsTheme
import com.example.spaceapps.ui.theme.SpaceBackground
import com.example.spaceapps.ui.theme.SpaceColors
import com.example.spaceapps.ui.theme.SpaceTheme

/**
  * Splash screen. Shows a title and navigates automatically.
  * @param onSplashFinished function called after the 2-second delay.
  */
@Composable
fun Splash(onSplashFinished: () -> Unit) {
    // Execute function 1.5 seconds after entering the screen
    LaunchedEffect(Unit) {
        kotlinx.coroutines.delay(1500)
        onSplashFinished()
    }

    val colors = SpaceTheme.colors

    SpaceBackground(showDecorativeCircles = true) {
        Box(
            modifier = Modifier.fillMaxSize().padding(32.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Row {
                    Text(
                        text = "Space",
                        style = MaterialTheme.typography.displayMedium,
                        fontWeight = FontWeight.ExtraBold,
                        color = colors.textPrimary
                    )
                    Text(
                        text = "X",
                        style = MaterialTheme.typography.displayMedium,
                        fontWeight = FontWeight.ExtraBold,
                        color = SpaceColors.PrimaryLight
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(R.string.app_name),
                    style = MaterialTheme.typography.titleMedium,
                    color = colors.textMuted
                )
                Spacer(modifier = Modifier.height(32.dp))
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(32.dp),
                    strokeWidth = 3.dp
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSplash() {
    SpaceAppsTheme {
        Splash(onSplashFinished = {})
    }
}