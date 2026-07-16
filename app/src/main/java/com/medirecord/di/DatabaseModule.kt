package com.medirecord.di

import android.content.Context
import androidx.room.Room
import com.medirecord.data.MediRecordDatabase
import com.medirecord.data.dao.MedicationDao
import com.medirecord.data.dao.MedicationLogDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): MediRecordDatabase {
        return Room.databaseBuilder(
            context,
            MediRecordDatabase::class.java,
            "medirecord_database"
        ).build()
    }

    @Provides
    fun provideMedicationDao(database: MediRecordDatabase): MedicationDao {
        return database.medicationDao()
    }

    @Provides
    fun provideMedicationLogDao(database: MediRecordDatabase): MedicationLogDao {
        return database.medicationLogDao()
    }
}
