package com.rickyhu.hdriver.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.rickyhu.hdriver.data.model.CarItem
import kotlinx.coroutines.flow.Flow

@Dao
interface CarItemDao {
    @Query("SELECT * FROM car_list_table ORDER BY lastViewedTime DESC")
    fun getCarList(): Flow<List<CarItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: CarItem)

    @Update
    suspend fun update(item: CarItem)

    @Delete
    suspend fun delete(item: CarItem)
}
