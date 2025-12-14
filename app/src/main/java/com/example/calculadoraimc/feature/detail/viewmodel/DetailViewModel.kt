package com.example.calculadoraimc.feature.detail.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.calculadoraimc.data.local.entity.IMCHistory
import com.example.calculadoraimc.data.repository.IMCHistoryRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class DetailViewModel(repository: IMCHistoryRepository, historyId: Int) : ViewModel() {

    val details: StateFlow<IMCHistory?> = repository.getHistoryDetails(historyId)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )
}
