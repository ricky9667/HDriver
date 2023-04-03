package com.rickyhu.hdriver.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.rickyhu.hdriver.data.database.AppDatabase
import com.rickyhu.hdriver.data.database.DatabaseProvider
import com.rickyhu.hdriver.data.model.CarItem
import kotlinx.coroutines.launch

class RecentListViewModel(db: AppDatabase) : ViewModel() {

    private val godItemDao = db.godItemDao()
    var onRecentListChanged: (List<CarItem>) -> Unit = {}

    init {
        registerRecentListUpdateEvent()
    }

    private fun registerRecentListUpdateEvent() {
        viewModelScope.launch {
            godItemDao.getRecentList().collect() {
                onRecentListChanged(it)
            }
        }
    }

    fun addRecentItem(number: String, url: String) {
        if (number.isEmpty()) return

        val newItem = CarItem(number = number, url = url)
        viewModelScope.launch {
            godItemDao.insert(newItem)
        }
    }

    fun deleteRecentItem(item: CarItem) {
        viewModelScope.launch {
            godItemDao.delete(item)
        }
    }
}

class RecentListViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RecentListViewModel::class.java)) {
            val db = DatabaseProvider.getDatabase(context)

            @Suppress("UNCHECKED_CAST")
            return RecentListViewModel(db) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
