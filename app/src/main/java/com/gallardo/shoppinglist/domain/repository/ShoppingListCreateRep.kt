package com.gallardo.shoppinglist.domain.repository

import com.gallardo.shoppinglist.domain.model.ShoppingList
import com.gallardo.shoppinglist.domain.model.ShoppingListItem
import kotlinx.coroutines.flow.Flow

interface ShoppingListCreateRep {
    suspend fun createShoppingList(list: ShoppingList) : Long

    suspend fun upsertShoppingListItem(item: List<ShoppingListItem>)

    suspend fun getShoppingListID(rowID: Long) : Int
}