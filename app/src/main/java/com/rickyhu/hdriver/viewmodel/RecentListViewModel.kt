package com.rickyhu.hdriver.viewmodel

import androidx.lifecycle.*
import com.rickyhu.hdriver.data.dao.GodItemDao
import com.rickyhu.hdriver.data.database.AppDatabase
import com.rickyhu.hdriver.data.model.GodItem
import kotlinx.coroutines.launch

class RecentListViewModel(private val itemDao: GodItemDao) : ViewModel() {

    private val db = AppDatabase.getDatabase()
    //    private val _godNumberList = MutableLiveData<List<GodItem>>()
    val godNumberList = itemDao.getRecentList()

    fun addRecentItem(number: String, url: String) {
        if (number.isEmpty()) return

        val newItem = GodItem(number, url)
        viewModelScope.launch {
            itemDao.insert(newItem)
        }
//        _godNumberList.value = _godNumberList.value?.plus(newItem) ?: listOf(newItem)
    }
}

class RecentListViewModelFactory(private val itemDao: GodItemDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RecentListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RecentListViewModel(itemDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}