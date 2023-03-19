package com.rickyhu.hdriver

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RecentListViewModel : ViewModel() {
    private val _godNumberList = MutableLiveData<List<RecentListItem>>()
    val godNumberList: LiveData<List<RecentListItem>> = _godNumberList

    fun addRecentItem(number: String) {
        if (number.isEmpty()) return
        val newItem = RecentListItem(number)
        _godNumberList.value = _godNumberList.value?.plus(newItem) ?: listOf(newItem)
    }
}
