package com.gallardo.shoppinglist.data.database.dao

import androidx.room.*
import com.gallardo.shoppinglist.data.database.model.ShoppingListItemEntity

@Dao
interface ShoppingListItemDao {

    @Query("SELECT * FROM ShoppingListItemEntity WHERE id = :id")
    suspend fun getShoppingListItem(id: Int): ShoppingListItemEntity

    @Query("SELECT * FROM ShoppingListItemEntity WHERE listId = :listId")
    suspend fun getShoppingListItems(listId: Int): List<ShoppingListItemEntity>

    @Upsert
    suspend fun upsertShoppingListItems(items: List<ShoppingListItemEntity>)

    @Upsert
    suspend fun upsertShoppingListItem(item: ShoppingListItemEntity)

    @Delete
    suspend fun deleteShoppingListItem(item: ShoppingListItemEntity)
}