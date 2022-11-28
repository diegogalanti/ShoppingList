package com.gallardo.shoppinglist.data.database.dao

import androidx.room.*
import com.gallardo.shoppinglist.data.database.model.ShoppingListEntity
import com.gallardo.shoppinglist.data.database.model.ShoppingListItemEntity

@Dao
interface ShoppingListItemDao {


    @Query("SELECT * FROM ShoppingListItemEntity WHERE id = :id")
    suspend fun getShoppingListItem(id: Int): ShoppingListItemEntity

    @Query("SELECT id FROM ShoppingListEntity WHERE rowId = :rowId")
    suspend fun getShoppingListID(rowId: Long): Int

    @Upsert
    suspend fun upsertShoppingListItem(items: List<ShoppingListItemEntity>)

    @Upsert
    suspend fun upsertShoppingList(list: ShoppingListEntity) : Long

    @Delete
    suspend fun deleteShoppingListItem(item: ShoppingListItemEntity)
}