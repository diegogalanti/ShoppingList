package com.gallardo.shoppinglist.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ShoppingListEntity(
    val name: String,
    val description: String?,
    val color: Int,
    val type: String,
    @PrimaryKey(autoGenerate = true) val id: Int
)
