package com.rickyhu.hdriver.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "car_list_table")
data class CarItem(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val url: String,
    val number: String,
    val lastViewedTime: Long = System.currentTimeMillis()
)
