package com.ikemenguitarist.pantrymanager.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipe")
data class Recipe(
    @PrimaryKey(autoGenerate = true)
    val id: Int=0,
    val recipeName:String,

)
