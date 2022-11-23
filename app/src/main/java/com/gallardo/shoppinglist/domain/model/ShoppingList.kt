package com.gallardo.shoppinglist.domain.model

import com.gallardo.shoppinglist.data.database.model.ShoppingListEntity

data class ShoppingList(
    val name: String,
    val description: String?,
    val color: Int,
    val type: String,
    val timestamp: Long,
    val id: Int
)

fun ShoppingList.asDatabaseEntity() = ShoppingListEntity(name, description, color, type, timestamp, id)
