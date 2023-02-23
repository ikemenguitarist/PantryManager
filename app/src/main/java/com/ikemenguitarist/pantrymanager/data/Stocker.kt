package com.ikemenguitarist.pantrymanager.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "stocker")
data class Stocker(
    @PrimaryKey(autoGenerate = true)
    val id: Int=0,
    val name:String,
    val description:String
)
