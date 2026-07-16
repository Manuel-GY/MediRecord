package com.medirecord.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.medirecord.data.entity.Medication
import com.medirecord.data.entity.MedicationLog
import com.medirecord.data.repository.MedicationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: MedicationRepository
) : ViewModel() {

    private val _medications = MutableStateFlow<List<Medication>>(emptyList())
    val medications: StateFlow<List<Medication>> = _medications.asStateFlow()

    private val _todayLogs = MutableStateFlow<List<MedicationLog>>(emptyList())
    val todayLogs: StateFlow<List<MedicationLog>> = _todayLogs.asStateFlow()

    private val _adFreeUntil = MutableStateFlow(0L)
    val adFreeUntil: StateFlow<Long> = _adFreeUntil.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getActiveMedications().collect { meds ->
                _medications.value = meds
            }
        }

        viewModelScope.launch {
            val today = getTodayRange()
            repository.getLogsByDateRange(today.first, today.second).collect { logs ->
                _todayLogs.value = logs
            }
        }
    }

    fun markAsTaken(medicationId: Long, scheduledTime: String) {
        viewModelScope.launch {
            val today = getTodayRange()
            val existingLog = _todayLogs.value.find {
                it.medicationId == medicationId && it.scheduledTime == scheduledTime && !it.wasTaken
            }

            if (existingLog != null) {
                repository.updateLog(
                    existingLog.copy(
                        wasTaken = true,
                        takenTime = System.currentTimeMillis()
                    )
                )
            } else {
                repository.insertLog(
                    MedicationLog(
                        medicationId = medicationId,
                        scheduledTime = scheduledTime,
                        wasTaken = true,
                        takenTime = System.currentTimeMillis(),
                        date = System.currentTimeMillis()
                    )
                )
            }
        }
    }

    fun markAsMissed(medicationId: Long, scheduledTime: String) {
        viewModelScope.launch {
            val existingLog = _todayLogs.value.find {
                it.medicationId == medicationId && it.scheduledTime == scheduledTime && !it.wasTaken
            }

            if (existingLog == null) {
                repository.insertLog(
                    MedicationLog(
                        medicationId = medicationId,
                        scheduledTime = scheduledTime,
                        wasTaken = false,
                        date = System.currentTimeMillis()
                    )
                )
            }
        }
    }

    private fun getTodayRange(): Pair<Long, Long> {
        val cal = Calendar.getInstance()
        cal.set(Calendar.HOUR_OF_DAY, 0)
        cal.set(Calendar.MINUTE, 0)
        cal.set(Calendar.SECOND, 0)
        cal.set(Calendar.MILLISECOND, 0)
        val start = cal.timeInMillis

        cal.add(Calendar.DAY_OF_MONTH, 1)
        val end = cal.timeInMillis

        return Pair(start, end)
    }
}
