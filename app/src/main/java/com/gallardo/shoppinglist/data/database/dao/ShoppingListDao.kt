package com.gallardo.shoppinglist.data.database.dao

import androidx.room.*
import com.gallardo.shoppinglist.data.database.model.ShoppingListEntity
import com.gallardo.shoppinglist.data.database.model.ShoppingListItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ShoppingListDao {
    @Query("SELECT * FROM ShoppingListEntity")
    fun getShoppingLists() : Flow<List<ShoppingListEntity>>

    @Query("SELECT * FROM ShoppingListEntity WHERE id = :id")
    suspend fun getShoppingList(id: Int): ShoppingListEntity

    @Query("SELECT * FROM ShoppingListEntity" +
            " LEFT JOIN ShoppingListItemEntity ON ShoppingListEntity.id = ShoppingListItemEntity.listId" +
            " WHERE ShoppingListEntity.id = :id")
    fun getShoppingListWithItems(id: Int): Flow<Map<ShoppingListEntity, List<ShoppingListItemEntity>>>

    @Upsert
    suspend fun upsertShoppingList(list: ShoppingListEntity) : Long

    @Delete
    suspend fun deleteShoppingList(list: ShoppingListEntity)

    @Query("SELECT id FROM ShoppingListEntity WHERE rowId = :rowId")
    suspend fun getShoppingListID(rowId: Long): Int
}