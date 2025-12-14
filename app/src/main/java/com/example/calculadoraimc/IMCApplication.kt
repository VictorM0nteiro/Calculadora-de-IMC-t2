package com.example.calculadoraimc

import android.app.Application
import com.example.calculadoraimc.data.local.AppDatabase
import com.example.calculadoraimc.data.repository.IMCHistoryRepository

class IMCApplication : Application() {
    // Usando lazy para que o banco de dados e o repositório sejam criados apenas quando forem necessários
    val database by lazy { AppDatabase.getDatabase(this) }
    val repository by lazy { IMCHistoryRepository(database.imcHistoryDao()) }
}
