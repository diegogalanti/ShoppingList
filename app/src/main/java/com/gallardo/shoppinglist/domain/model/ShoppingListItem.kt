package com.gallardo.shoppinglist.domain.model

import com.gallardo.shoppinglist.data.database.model.ShoppingListItemEntity

data class ShoppingListItem(
    val description: String,
    val quantity: String?,
    val done: Boolean,
    val listId: Int,
    val id: Int?
)

fun ShoppingListItem.asDatabaseEntity() = ShoppingListItemEntity(description, quantity, done, listId, id)
