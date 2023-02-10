package com.gallardo.shoppinglist.presentation.state

import com.gallardo.shoppinglist.domain.model.ShoppingList
import com.gallardo.shoppinglist.domain.model.ShoppingListItem


sealed interface ShoppingListViewUiState {
    object Loading : ShoppingListViewUiState
    data class Success(
        val shoppingList: ShoppingList,
        val items: List<ShoppingListItem>
    ) : ShoppingListViewUiState
}