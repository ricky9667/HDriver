package com.rickyhu.hdriver.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Timestamp

@Entity(tableName = "god_item_table", primaryKeys = ["number"])
data class GodItem(
    @PrimaryKey val url: String,
    val number: String,
    val timestamp: Timestamp = Timestamp(System.currentTimeMillis()),
)
