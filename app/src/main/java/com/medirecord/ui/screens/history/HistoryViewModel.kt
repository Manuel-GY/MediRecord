package com.medirecord.ui.screens.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.medirecord.data.entity.MedicationLog
import com.medirecord.data.repository.MedicationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val repository: MedicationRepository
) : ViewModel() {

    private val _logs = MutableStateFlow<List<MedicationLog>>(emptyList())
    val logs: StateFlow<List<MedicationLog>> = _logs.asStateFlow()

    private val _selectedDate = MutableStateFlow(Calendar.getInstance())
    val selectedDate: StateFlow<Calendar> = _selectedDate.asStateFlow()

    init {
        loadLogs()
    }

    fun setSelectedDate(calendar: Calendar) {
        _selectedDate.value = calendar
        loadLogs()
    }

    private fun loadLogs() {
        viewModelScope.launch {
            val cal = _selectedDate.value.clone() as Calendar
            cal.set(Calendar.HOUR_OF_DAY, 0)
            cal.set(Calendar.MINUTE, 0)
            cal.set(Calendar.SECOND, 0)
            cal.set(Calendar.MILLISECOND, 0)
            val start = cal.timeInMillis

            cal.add(Calendar.DAY_OF_MONTH, 1)
            val end = cal.timeInMillis

            repository.getLogsByDateRange(start, end).collect {
                _logs.value = it
            }
        }
    }
}
