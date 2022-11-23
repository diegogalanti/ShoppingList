package com.gallardo.shoppinglist.domain.repository

import com.gallardo.shoppinglist.domain.model.ShoppingListItem
import kotlinx.coroutines.flow.Flow

interface ShoppingListItemRep {
    fun getShoppingListItems(): Flow<List<ShoppingListItem>>

    suspend fun getShoppingListItem(id: Int): ShoppingListItem

    suspend fun insertShoppingListItem(item: ShoppingListItem)

    suspend fun deleteShoppingListItem(item: ShoppingListItem)
}