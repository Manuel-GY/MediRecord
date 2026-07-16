package com.medirecord.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.medirecord.data.dao.MedicationDao
import com.medirecord.data.dao.MedicationLogDao
import com.medirecord.data.entity.Medication
import com.medirecord.data.entity.MedicationLog

@Database(
    entities = [Medication::class, MedicationLog::class],
    version = 1,
    exportSchema = false
)
abstract class MediRecordDatabase : RoomDatabase() {
    abstract fun medicationDao(): MedicationDao
    abstract fun medicationLogDao(): MedicationLogDao
}
