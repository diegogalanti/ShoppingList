package com.gallardo.shoppinglist.presentation.navigation

sealed class Screen (val route: String) {
    object ShoppingListSummaryScreen : Screen("list_route")
    object ShoppingListCreateScreen : Screen("create_route")
    object ShoppingListViewScreen : Screen("view_route")
}
