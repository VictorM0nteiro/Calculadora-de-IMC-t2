package com.example.calculadoraimc.feature.detail.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.calculadoraimc.data.repository.IMCHistoryRepository

class DetailViewModelFactory(
    private val repository: IMCHistoryRepository,
    private val historyId: Int
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DetailViewModel(repository, historyId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
