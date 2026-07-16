package com.medirecord.ui.screens.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.medirecord.data.entity.Medication
import com.medirecord.data.repository.MedicationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddMedicationViewModel @Inject constructor(
    private val repository: MedicationRepository
) : ViewModel() {

    private val _saveSuccess = MutableStateFlow(false)
    val saveSuccess: StateFlow<Boolean> = _saveSuccess.asStateFlow()

    fun saveMedication(
        name: String,
        dosage: String,
        instructions: String,
        times: String,
        color: Int
    ) {
        viewModelScope.launch {
            repository.insertMedication(
                Medication(
                    name = name,
                    dosage = dosage,
                    instructions = instructions,
                    times = times,
                    color = color
                )
            )
            _saveSuccess.value = true
        }
    }
}
