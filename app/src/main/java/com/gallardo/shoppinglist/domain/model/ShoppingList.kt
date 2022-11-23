package com.gallardo.shoppinglist.domain.model

data class ShoppingList(
    val name: String,
    val description: String?,
    val color: Int,
    val type: String,
    val id: Int
)
