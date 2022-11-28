package com.gallardo.shoppinglist.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gallardo.shoppinglist.presentation.screen.CreateShoppingListScreen
import com.gallardo.shoppinglist.presentation.screen.ShoppingListScreen

@Composable
fun ShoppingListNav(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.ShoppingListScreen.route) {
        composable(route = Screen.ShoppingListScreen.route) {
            ShoppingListScreen(modifier) { navController.navigate(Screen.ShoppingListCreateScreen.route) }
        }
        composable(route = Screen.ShoppingListCreateScreen.route) {
            CreateShoppingListScreen(modifier) { navController.navigateUp() }
        }
    }
}