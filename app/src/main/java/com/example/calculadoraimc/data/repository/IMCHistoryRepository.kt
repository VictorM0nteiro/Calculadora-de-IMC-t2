package com.example.calculadoraimc.data.repository

import com.example.calculadoraimc.data.local.dao.IMCHistoryDao
import com.example.calculadoraimc.data.local.entity.IMCHistory
import kotlinx.coroutines.flow.Flow

class IMCHistoryRepository(private val imcHistoryDao: IMCHistoryDao) {

    fun getAllHistory(): Flow<List<IMCHistory>> = imcHistoryDao.getAll()

    fun getHistoryDetails(id: Int): Flow<IMCHistory?> = imcHistoryDao.getHistoryById(id)

    suspend fun insertHistory(imcHistory: IMCHistory) {
        imcHistoryDao.insert(imcHistory)
    }
}
