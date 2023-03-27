package com.rickyhu.hdriver.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "god_item_table")
data class GodItem(
    @PrimaryKey val url: String,
    val number: String
)
