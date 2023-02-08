package com.gallardo.shoppinglist.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gallardo.shoppinglist.domain.model.ShoppingList

@Entity
data class ShoppingListEntity(
    val name: String,
    val description: String?,
    val paperTexture: Int,
    val type: Int,
    val timestamp: Long,
    val penColor: Int,
    @PrimaryKey(autoGenerate = true) val id: Int?
)

fun ShoppingListEntity.asDomainModel() = ShoppingList(name, description, paperTexture, type, timestamp, penColor, id)
