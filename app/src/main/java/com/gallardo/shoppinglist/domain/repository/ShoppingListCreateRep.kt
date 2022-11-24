package com.gallardo.shoppinglist.domain.repository

import com.gallardo.shoppinglist.domain.model.ShoppingList
import com.gallardo.shoppinglist.domain.model.ShoppingListItem

interface ShoppingListCreateRep {
    suspend fun createShoppingList(list: ShoppingList) : Long

    suspend fun upsertShoppingListItem(items: List<ShoppingListItem>)

    suspend fun getShoppingListID(rowID: Long) : Int
}