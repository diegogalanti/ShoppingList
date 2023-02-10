package com.gallardo.shoppinglist.presentation.event

import com.gallardo.shoppinglist.domain.model.ShoppingListItem

sealed interface ShoppingListViewEvent {
    data class ItemClickEvent(val shoppingListItem: ShoppingListItem): ShoppingListViewEvent

}