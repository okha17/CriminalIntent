package com.bignerdranch.android.criminalintent.Database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.bignerdranch.android.criminalintent.Crime

@Database(entities = [ Crime::class ], version=1)
@TypeConverters(KHCrimeTypeConverters::class)
abstract class KHCrimeDatabase : RoomDatabase() {
    // register your DAO class with your database class.
    abstract fun crimeDao(): CrimeDao
}