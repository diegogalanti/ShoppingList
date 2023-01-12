package com.gallardo.shoppinglist.presentation.event

import androidx.compose.ui.graphics.Color

sealed interface ShoppingListCreateEvent {
    data class ColorChangeEvent(val color: Int): ShoppingListCreateEvent
    data class PenColorChangeEvent(val color: Color): ShoppingListCreateEvent
    data class NameChangeEvent(val name: String): ShoppingListCreateEvent
    data class DescriptionChangeEvent(val description: String): ShoppingListCreateEvent
    data class TypeChangeEvent(val type: Int): ShoppingListCreateEvent
    object ListSaveEvent: ShoppingListCreateEvent
    object ListCloseEvent: ShoppingListCreateEvent
    data class ItemDescriptionChangeEvent(val index: Int, val description: String): ShoppingListCreateEvent
    data class ItemQuantityChangeEvent(val index: Int, val quantity: String): ShoppingListCreateEvent
}