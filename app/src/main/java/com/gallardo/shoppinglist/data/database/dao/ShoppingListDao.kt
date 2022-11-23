package com.gallardo.shoppinglist.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gallardo.shoppinglist.data.database.model.ShoppingListEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ShoppingListDao {
    @Query("SELECT * FROM ShoppingListEntity")
    fun getShoppingLists() : Flow<List<ShoppingListEntity>>

    @Query("SELECT * FROM ShoppingListEntity WHERE id = :id")
    suspend fun getShoppingList(id: Int): ShoppingListEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShoppingList(list: ShoppingListEntity)

    @Delete
    suspend fun deleteShoppingList(list: ShoppingListEntity)
}