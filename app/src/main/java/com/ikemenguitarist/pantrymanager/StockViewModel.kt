package com.ikemenguitarist.pantrymanager

import androidx.lifecycle.*
import com.ikemenguitarist.pantrymanager.data.Item
import com.ikemenguitarist.pantrymanager.data.ItemDao
import com.ikemenguitarist.pantrymanager.data.Stocker
import com.ikemenguitarist.pantrymanager.data.StockerDao
import kotlinx.coroutines.launch
import java.util.*

class StockViewModel (private val itemDao: ItemDao,private val stockerDao: StockerDao):ViewModel(){
    val allItems: LiveData<List<Item>> = itemDao.getItems().asLiveData()
    val allStockers:LiveData<List<Stocker>> = stockerDao.getStockers().asLiveData()

    private var _currentStocker: MutableLiveData<String> = MutableLiveData<String>()
    val currentStocker: LiveData<String>
        get() = _currentStocker

    private var _currentState: MutableLiveData<String> = MutableLiveData<String>()
    val currentState: LiveData<String>
        get() = _currentState

    private var _currentItemId: MutableLiveData<Int> = MutableLiveData<Int>()
    val currentItemId: LiveData<Int>
        get() = _currentItemId

    fun addNewItem(itemName: String, itemLimit:Date,itemCount: String) {
        val newItem = getNewItemEntry(itemName, itemLimit,itemCount)
        insertItem(newItem)
    }

    private fun insertItem(item: Item){
        viewModelScope.launch {
            itemDao.insert(item)
        }
    }

    fun retrieveItem(id: Int): LiveData<Item> {
        return itemDao.getItem(id).asLiveData()
    }

    fun isEntryValid(itemName: String, itemPrice: String, itemCount: String): Boolean {
        if (itemName.isBlank() || itemPrice.isBlank() || itemCount.isBlank()) {
            return false
        }
        return true
    }

    private fun getNewItemEntry(itemName:String,itemLimit:Date,itemCount:String):Item{
        return Item(
            itemName = itemName,
            itemQuantity =itemCount.toInt(),
            limit = itemLimit,
            stocker = currentStocker.value.toString()
        )
    }
    fun updateItem(
        itemId: Int,
        itemName: String,
        itemLimit: Date,
        itemCount: String
    ) {
        val updatedItem = getUpdatedItemEntry(itemId, itemName, itemLimit, itemCount)
        updateItem(updatedItem)
    }


    /**
     * Launching a new coroutine to update an item in a non-blocking way
     */
    private fun updateItem(item: Item) {
        viewModelScope.launch {
            itemDao.update(item)
        }
    }
    private fun getUpdatedItemEntry(
        itemId: Int,
        itemName: String,
        itemLimit: Date,
        itemCount: String
    ): Item {
        return Item(
            id = itemId,
            itemName = itemName,
            limit = itemLimit,
            itemQuantity = itemCount.toInt(),
            stocker = currentStocker.value.toString()
        )
    }
    fun updateCurrentState(state:String){
        _currentState.value=state
    }

}