package com.gallardo.shoppinglist.domain.model


data class ShoppingListItemSimple(
    val description: String = "",
    val quantity: Float? = null,
    val unit: String? = null
)

fun ShoppingListItemSimple.asShoppingList(listID: Int) = ShoppingListItem(description, quantity, unit, false, listID, null)

