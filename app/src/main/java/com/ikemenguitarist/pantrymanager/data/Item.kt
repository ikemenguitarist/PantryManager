package com.ikemenguitarist.pantrymanager.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "item")
data class Item(
    @PrimaryKey(autoGenerate = true)
    val id: Int=0,
    @ColumnInfo(name = "name")
    val itemName: String,
    @ColumnInfo(name = "quantity")
    val itemQuantity: Int,
    val limit: Date,
    val stocker: String
)
