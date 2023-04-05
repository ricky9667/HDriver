package com.rickyhu.hdriver.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "car_list_table")
data class CarItem(
    @PrimaryKey val url: String,
    val number: String,
    val lastViewedTime: Long = System.currentTimeMillis()
)
