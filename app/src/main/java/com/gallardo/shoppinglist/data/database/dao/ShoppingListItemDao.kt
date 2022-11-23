package com.gallardo.shoppinglist.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gallardo.shoppinglist.data.database.model.ShoppingListItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ShoppingListItemDao {
    @Query("SELECT * FROM ShoppingListItemEntity")
    fun getShoppingListItems(): Flow<List<ShoppingListItemEntity>>

    @Query("SELECT * FROM ShoppingListItemEntity WHERE id = :id")
    suspend fun getShoppingListItem(id: Int): ShoppingListItemEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShoppingListItem(item: ShoppingListItemEntity)

    @Delete
    suspend fun deleteShoppingListItem(item: ShoppingListItemEntity)
}