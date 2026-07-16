package com.medirecord.data.repository

import com.medirecord.data.dao.MedicationDao
import com.medirecord.data.dao.MedicationLogDao
import com.medirecord.data.entity.Medication
import com.medirecord.data.entity.MedicationLog
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MedicationRepository @Inject constructor(
    private val medicationDao: MedicationDao,
    private val medicationLogDao: MedicationLogDao
) {
    fun getActiveMedications(): Flow<List<Medication>> = medicationDao.getActiveMedications()

    fun getAllMedications(): Flow<List<Medication>> = medicationDao.getAllMedications()

    suspend fun getMedicationById(id: Long): Medication? = medicationDao.getMedicationById(id)

    suspend fun insertMedication(medication: Medication): Long = medicationDao.insert(medication)

    suspend fun updateMedication(medication: Medication) = medicationDao.update(medication)

    suspend fun deleteMedication(medication: Medication) = medicationDao.delete(medication)

    suspend fun deactivateMedication(id: Long) = medicationDao.deactivate(id)

    fun getLogsByDateRange(startDate: Long, endDate: Long): Flow<List<MedicationLog>> =
        medicationLogDao.getLogsByDateRange(startDate, endDate)

    fun getLogsByMedication(medicationId: Long, startDate: Long, endDate: Long): Flow<List<MedicationLog>> =
        medicationLogDao.getLogsByMedication(medicationId, startDate, endDate)

    fun getTakenLogs(startDate: Long, endDate: Long): Flow<List<MedicationLog>> =
        medicationLogDao.getTakenLogs(startDate, endDate)

    fun getTakenCount(startDate: Long, endDate: Long): Flow<Int> =
        medicationLogDao.getTakenCount(startDate, endDate)

    fun getTotalScheduled(startDate: Long, endDate: Long): Flow<Int> =
        medicationLogDao.getTotalScheduled(startDate, endDate)

    suspend fun insertLog(log: MedicationLog): Long = medicationLogDao.insert(log)

    suspend fun updateLog(log: MedicationLog) = medicationLogDao.update(log)

    suspend fun deleteLog(log: MedicationLog) = medicationLogDao.delete(log)
}
