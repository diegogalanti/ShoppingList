package com.gallardo.shoppinglist.domain.model

data class ShoppingListItem(
    val description: String,
    val quantity: Int?,
    val unit: String?,
    val done: Boolean,
    val listId: Int,
    val id: Int
)
