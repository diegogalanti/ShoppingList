package com.gallardo.shoppinglist.presentation.event

import com.gallardo.shoppinglist.domain.model.ShoppingList

sealed interface ShoppingListEvent {
    data class DeleteListEvent(val list: ShoppingList): ShoppingListEvent
    object UndoDeleteListEvent: ShoppingListEvent
}

sealed interface ShoppingListNavigationEvent {
    data class OpenListEvent(val listId: Int): ShoppingListNavigationEvent
    object CreateEditListEvent: ShoppingListNavigationEvent

    object CloseEvent: ShoppingListNavigationEvent
}
