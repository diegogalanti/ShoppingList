package com.gallardo.shoppinglist.domain.model

import com.gallardo.shoppinglist.data.database.model.ShoppingListEntity

data class ShoppingList(
    val name: String,
    val description: String?,
    val paperTexture: Int,
    val type: Int,
    val timestamp: Long,
    val penColor: Int,
    val id: Int?
)

fun ShoppingList.asDatabaseEntity() = ShoppingListEntity(name, description, paperTexture, type, timestamp, penColor, id)
