package com.gallardo.shoppinglist.presentation.event

import com.gallardo.shoppinglist.domain.model.ShoppingList

sealed interface ShoppingListEvent {
    data class DeleteListEvent(val list: ShoppingList): ShoppingListEvent
    object UndoDeleteListEvent: ShoppingListEvent
}
