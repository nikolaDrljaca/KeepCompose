package com.example.keepcompose

import androidx.compose.runtime.*
import androidx.navigation.NavType
import androidx.navigation.compose.*
import com.example.keepcompose.Screens.*
import com.example.keepcompose.ui.screens.detail.Detail
import com.example.keepcompose.ui.screens.simplehome.SimpleHome

@Composable
fun AppNavigator() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = SimpleHomeScreen.route
    ) {
        composable(SimpleHomeScreen.route) {
            SimpleHome(
                onNoteClicked = { noteId ->
                    navController.navigate(SimpleDetailScreen.route.plus("/?noteId=$noteId"))

                },
                onAddNewSimpleNote = {
                    navController.navigate(SimpleDetailScreen.route.plus("/?noteId="))
                }
            )
        }

        composable(
            route = SimpleDetailScreen.route.plus("/?noteId={noteId}"),
            arguments = listOf(
                navArgument("noteId") {
                    type = NavType.IntType
                    defaultValue = -1
                }
            )
        ) {
            Detail(
                onBackArrowPressed = { navController.navigateUp() }
            )
        }
    }
}

sealed class Screens(val route: String) {
    object SimpleHomeScreen : Screens("home")
    object SimpleDetailScreen : Screens("detail")
}
