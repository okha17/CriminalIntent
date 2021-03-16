package com.bignerdranch.android.criminalintent.Database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.bignerdranch.android.criminalintent.Crime
import java.util.*

@Dao
interface CrimeDao {
    // By returning an instance of LiveData from your DAO class, you signal Room to run your query on
    // a background thread.
    // When the query completes, the LiveData object will handle sending the crime data
    // over to the main thread and notify any observers
    @Query("SELECT * FROM crime")
    fun kh_getCrimes(): LiveData<List<Crime>>

    @Query("SELECT * FROM crime WHERE id=(:id)")
    fun kh_getCrime(id: UUID): LiveData<Crime?>
}
