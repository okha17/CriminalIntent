package com.bignerdranch.android.criminalintent

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.bignerdranch.android.criminalintent.Database.KHCrimeDatabase
import java.util.*

private const val KH_DATABASE_NAME = "crime-database"

class KHCrimeRepository private constructor(context: Context) {

    private val kh_database : KHCrimeDatabase = Room.databaseBuilder(
        context.applicationContext,
        KHCrimeDatabase::class.java,
        KH_DATABASE_NAME
    ).build()
    private val crimeDao = kh_database.crimeDao()

    fun kh_getCrimes(): LiveData<List<Crime>> = crimeDao.kh_getCrimes()

    fun kh_getCrime(id: UUID): LiveData<Crime?> = crimeDao.kh_getCrime(id)

    companion object {
        private var KH_INSTANCE: KHCrimeRepository? = null

        fun kh_initialize(context: Context) {
            if (KH_INSTANCE == null) {
                KH_INSTANCE = KHCrimeRepository(context)
            }
        }

        fun kh_get(): KHCrimeRepository {
            return KH_INSTANCE ?:throw IllegalStateException("CrimeRepository must be initialized")
        }
    }
}