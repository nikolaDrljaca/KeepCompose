package com.example.keepcompose

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.keepcompose.ui.theme.KeepComposeTheme
import androidx.activity.compose.setContent
import androidx.compose.animation.Crossfade
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.KEY_ROUTE

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KeepComposeTheme {
                AppNavigator()
            }
        }
    }
}