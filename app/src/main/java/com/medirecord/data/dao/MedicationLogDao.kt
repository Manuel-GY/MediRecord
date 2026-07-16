package com.medirecord.data.dao

import androidx.room.*
import com.medirecord.data.entity.MedicationLog
import kotlinx.coroutines.flow.Flow

@Dao
interface MedicationLogDao {
    @Query("SELECT * FROM medication_logs WHERE date BETWEEN :startDate AND :endDate ORDER BY date DESC")
    fun getLogsByDateRange(startDate: Long, endDate: Long): Flow<List<MedicationLog>>

    @Query("SELECT * FROM medication_logs WHERE medicationId = :medicationId AND date BETWEEN :startDate AND :endDate ORDER BY date DESC")
    fun getLogsByMedication(medicationId: Long, startDate: Long, endDate: Long): Flow<List<MedicationLog>>

    @Query("SELECT * FROM medication_logs WHERE date BETWEEN :startDate AND :endDate AND wasTaken = 1")
    fun getTakenLogs(startDate: Long, endDate: Long): Flow<List<MedicationLog>>

    @Query("SELECT COUNT(*) FROM medication_logs WHERE date BETWEEN :startDate AND :endDate AND wasTaken = 1")
    fun getTakenCount(startDate: Long, endDate: Long): Flow<Int>

    @Query("SELECT COUNT(*) FROM medication_logs WHERE date BETWEEN :startDate AND :endDate")
    fun getTotalScheduled(startDate: Long, endDate: Long): Flow<Int>

    @Insert
    suspend fun insert(log: MedicationLog): Long

    @Update
    suspend fun update(log: MedicationLog)

    @Delete
    suspend fun delete(log: MedicationLog)

    @Query("DELETE FROM medication_logs WHERE medicationId = :medicationId")
    suspend fun deleteByMedication(medicationId: Long)
}
