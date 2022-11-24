package com.gallardo.shoppinglist.data.database.dao

import androidx.room.*
import com.gallardo.shoppinglist.data.database.model.ShoppingListEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ShoppingListDao {
    @Query("SELECT * FROM ShoppingListEntity")
    fun getShoppingLists() : Flow<List<ShoppingListEntity>>

    @Query("SELECT * FROM ShoppingListEntity WHERE id = :id")
    suspend fun getShoppingList(id: Int): ShoppingListEntity

    @Upsert
    suspend fun upsertShoppingList(list: ShoppingListEntity)

    @Delete
    suspend fun deleteShoppingList(list: ShoppingListEntity)
}