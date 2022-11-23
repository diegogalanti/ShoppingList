package com.gallardo.shoppinglist.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gallardo.shoppinglist.domain.model.ShoppingList

@Entity
data class ShoppingListEntity(
    val name: String,
    val description: String?,
    val color: Int,
    val type: String,
    val timestamp: Long,
    @PrimaryKey(autoGenerate = true) val id: Int
)

fun ShoppingListEntity.asDomainModel() = ShoppingList(name, description, color, type, timestamp, id)
