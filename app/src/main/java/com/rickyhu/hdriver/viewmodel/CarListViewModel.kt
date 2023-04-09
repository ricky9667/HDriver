package com.rickyhu.hdriver.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.rickyhu.hdriver.data.database.AppDatabase
import com.rickyhu.hdriver.data.database.DatabaseProvider
import com.rickyhu.hdriver.data.model.CarItem
import kotlinx.coroutines.launch

class CarListViewModel(db: AppDatabase) : ViewModel() {
    private val carItemDao = db.carItemDao()
    var onRecentListChanged: (List<CarItem>) -> Unit = {}

    init {
        registerRecentListUpdateEvent()
    }

    private fun registerRecentListUpdateEvent() {
        viewModelScope.launch {
            carItemDao.getCarList().collect() {
                onRecentListChanged(it)
            }
        }
    }

    fun viewCarItem(number: String, url: String) {
        if (number.isEmpty()) return

        viewModelScope.launch {
            val item = carItemDao.getCarItem(number, url)
            if (item == null) {
                carItemDao.insert(CarItem(url = url, number = number))
            } else {
                carItemDao.update(item.copy(lastViewedTime = System.currentTimeMillis()))
            }
        }
    }

    fun updateCarItemViewTime(item: CarItem) {
        viewModelScope.launch {
            carItemDao.update(item.copy(lastViewedTime = System.currentTimeMillis()))
        }
    }

    fun setCarItemFavorite(item: CarItem, isFavorite: Boolean) {
        viewModelScope.launch {
            carItemDao.update(item.copy(isFavorite = isFavorite))
        }
    }

    fun deleteCarItem(item: CarItem) {
        viewModelScope.launch {
            carItemDao.delete(item)
        }
    }
}

class CarListViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CarListViewModel::class.java)) {
            val db = DatabaseProvider.getDatabase(context)

            @Suppress("UNCHECKED_CAST")
            return CarListViewModel(db) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
