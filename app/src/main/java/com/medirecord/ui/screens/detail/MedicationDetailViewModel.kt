package com.medirecord.ui.screens.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.medirecord.data.entity.Medication
import com.medirecord.data.entity.MedicationLog
import com.medirecord.data.repository.MedicationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MedicationDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: MedicationRepository
) : ViewModel() {

    private val medicationId: Long = savedStateHandle.get<Long>("medicationId") ?: 0L

    private val _medication = MutableStateFlow<Medication?>(null)
    val medication: StateFlow<Medication?> = _medication.asStateFlow()

    private val _logs = MutableStateFlow<List<MedicationLog>>(emptyList())
    val logs: StateFlow<List<MedicationLog>> = _logs.asStateFlow()

    private val _deleted = MutableStateFlow(false)
    val deleted: StateFlow<Boolean> = _deleted.asStateFlow()

    init {
        viewModelScope.launch {
            _medication.value = repository.getMedicationById(medicationId)
        }
        viewModelScope.launch {
            val cal = java.util.Calendar.getInstance()
            cal.add(java.util.Calendar.DAY_OF_MONTH, -30)
            repository.getLogsByMedication(medicationId, cal.timeInMillis, System.currentTimeMillis()).collect {
                _logs.value = it
            }
        }
    }

    fun deleteMedication() {
        viewModelScope.launch {
            _medication.value?.let {
                repository.deleteMedication(it)
                _deleted.value = true
            }
        }
    }
}
