package com.rickyhu.hdriver.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rickyhu.hdriver.data.model.CarItem
import kotlinx.coroutines.flow.Flow

@Dao
interface CarItemDao {
    @Query("SELECT * FROM car_list_table")
    fun getRecentList(): Flow<List<CarItem>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: CarItem)

    @Delete
    suspend fun delete(item: CarItem)
}
