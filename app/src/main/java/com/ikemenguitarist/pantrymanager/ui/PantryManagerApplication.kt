package com.ikemenguitarist.pantrymanager.ui

import android.app.Application
import com.ikemenguitarist.pantrymanager.data.ItemRoomDatabase

class PantryManagerApplication : Application() {
    val database:ItemRoomDatabase by lazy { ItemRoomDatabase.getDatabase(this) }
}