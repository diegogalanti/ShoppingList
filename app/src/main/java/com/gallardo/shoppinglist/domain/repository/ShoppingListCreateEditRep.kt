package com.gallardo.shoppinglist.domain.repository

import com.gallardo.shoppinglist.domain.model.ShoppingList
import com.gallardo.shoppinglist.domain.model.ShoppingListItem

interface ShoppingListCreateEditRep {

    suspend fun getShoppingList(listId : Int) : ShoppingList

    suspend fun getShoppingListItems(listId : Int) : List<ShoppingListItem>
    suspend fun upsertShoppingList(list: ShoppingList) : Long

    suspend fun upsertShoppingListItem(items: List<ShoppingListItem>)

    suspend fun getShoppingListID(rowID: Long) : Int
}