package com.gallardo.shoppinglist.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.gallardo.shoppinglist.presentation.event.ShoppingListNavigationEvent
import com.gallardo.shoppinglist.presentation.screen.ShoppingListCreateScreen
import com.gallardo.shoppinglist.presentation.screen.ShoppingListSummaryScreen
import com.gallardo.shoppinglist.presentation.screen.ShoppingListViewScreen

@Composable
fun ShoppingListNav(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.ShoppingListSummaryScreen.route) {
        composable(route = Screen.ShoppingListSummaryScreen.route) {
            ShoppingListSummaryScreen(modifier) { event ->
                when(event) {
                    is ShoppingListNavigationEvent.CreateListEvent -> navController.navigate(Screen.ShoppingListCreateScreen.route)
                    is ShoppingListNavigationEvent.OpenListEvent -> navController.navigate(Screen.ShoppingListViewScreen.route + "/${event.listId}")
                }
            }
        }
        composable(route = Screen.ShoppingListCreateScreen.route) {
            ShoppingListCreateScreen(modifier) { navController.navigateUp() }
        }
        composable(
            route = Screen.ShoppingListViewScreen.route + "/{listId}",
            arguments = listOf(navArgument("listId") { type = NavType.IntType })
        ) { backStackEntry ->
            ShoppingListViewScreen(modifier) { navController.navigateUp() }
        }
    }
}