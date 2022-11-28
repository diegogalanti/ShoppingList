package com.gallardo.shoppinglist.domain.model


data class ShoppingListItemSimple(
    val description: String = "",
    val quantity: String? = "1"
)

fun ShoppingListItemSimple.asShoppingList(listID: Int) = ShoppingListItem(description, quantity, false, listID, null)

