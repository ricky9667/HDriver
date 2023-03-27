package com.rickyhu.hdriver.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.rickyhu.hdriver.data.database.AppDatabase
import com.rickyhu.hdriver.data.database.DatabaseProvider
import com.rickyhu.hdriver.data.model.GodItem
import kotlinx.coroutines.launch

class RecentListViewModel(db: AppDatabase) : ViewModel() {

    private val godItemDao = db.godItemDao()
    var onRecentListChanged: (List<GodItem>) -> Unit = {}

    init {
        registerRecentListUpdateEvent()
    }

    private fun registerRecentListUpdateEvent() {
        viewModelScope.launch {
            godItemDao.getRecentList().collect() { onRecentListChanged(it) }
        }
    }

    fun addRecentItem(number: String, url: String) {
        if (number.isEmpty()) return

        Log.d("RecentListViewModel", "addRecentItem: $number, $url")
        val newItem = GodItem(number, url)
        viewModelScope.launch {
            godItemDao.insert(newItem)
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
