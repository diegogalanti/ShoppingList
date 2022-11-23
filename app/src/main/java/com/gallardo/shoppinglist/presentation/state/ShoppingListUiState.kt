package com.gallardo.shoppinglist.presentation.state

import com.gallardo.shoppinglist.domain.model.ShoppingList

sealed interface ShoppingListUiState {
    object Loading : ShoppingListUiState
    data class Success(val shoppingLists: List<ShoppingList>) : ShoppingListUiState
}