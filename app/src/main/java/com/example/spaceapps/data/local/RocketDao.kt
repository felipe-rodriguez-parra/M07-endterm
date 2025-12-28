package com.example.spaceapps.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface RocketDao {
    @Query("SELECT * FROM rockets ORDER BY name ASC")
    fun observeRockets(): Flow<List<RocketEntity>>

    @Query("SELECT * FROM rockets WHERE id = :id LIMIT 1")
    fun observeRocket(id: String): Flow<RocketEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(entities: List<RocketEntity>)

    @Query("DELETE FROM rockets")
    suspend fun clear()
}
