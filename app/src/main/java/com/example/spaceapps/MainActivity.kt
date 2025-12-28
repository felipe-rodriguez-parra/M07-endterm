package com.example.spaceapps

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.spaceapps.navigation.NavGraph
import com.example.spaceapps.ui.theme.SpaceAppsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SpaceAppsTheme {
                val navController = rememberNavController()
                NavGraph(navController = navController)
            }
        }
    }
}
