package com.gallardo.shoppinglist.presentation.navigation

sealed class Screen (val route: String) {
    object ShoppingListScreen : Screen("list_route")
    object ShoppingListCreateScreen : Screen("create_route")
}
