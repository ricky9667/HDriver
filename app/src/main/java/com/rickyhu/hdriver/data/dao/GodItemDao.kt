package com.rickyhu.hdriver.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rickyhu.hdriver.data.model.GodItem
import kotlinx.coroutines.flow.Flow

@Dao
interface GodItemDao {
    @Query("SELECT * FROM god_item_table")
    fun getRecentList(): Flow<List<GodItem>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: GodItem)

    @Delete
    suspend fun delete(item: GodItem)
}