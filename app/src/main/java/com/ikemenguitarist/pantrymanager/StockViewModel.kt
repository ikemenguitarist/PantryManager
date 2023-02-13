package com.ikemenguitarist.pantrymanager

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.ikemenguitarist.pantrymanager.data.Item
import com.ikemenguitarist.pantrymanager.data.ItemDao
import com.ikemenguitarist.pantrymanager.data.Stocker
import kotlinx.coroutines.launch
import java.util.*

class StockViewModel (private val itemDao: ItemDao):ViewModel(){
    val allItems: LiveData<List<Item>> = itemDao.getItems().asLiveData()

    private fun insertItem(item: Item){
        viewModelScope.launch {
            itemDao.insert(item)
        }
    }
    private fun getNewEntry(itemName:String,itemCount:String,itemLimit:Date,itemStocker:String):Item{
        return Item(
            itemName = itemName,
            itemQuantity =itemCount.toInt(),
            limit = itemLimit,
            stocker = itemStocker
        )
    }
}