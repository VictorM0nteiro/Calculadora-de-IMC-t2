package com.example.calculadoraimc.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.calculadoraimc.data.local.entity.IMCHistory
import kotlinx.coroutines.flow.Flow

@Dao
interface IMCHistoryDao {

    @Insert
    suspend fun insert(imcHistory: IMCHistory)

    @Query("SELECT * FROM imc_history ORDER BY date DESC")
    fun getAll(): Flow<List<IMCHistory>>

    @Query("SELECT * FROM imc_history WHERE id = :id")
    fun getHistoryById(id: Int): Flow<IMCHistory?>
}
