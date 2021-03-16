package com.bignerdranch.android.criminalintent

import androidx.lifecycle.ViewModel

class KHCrimeListViewModel: ViewModel() {
    private val kh_crimeRepository = KHCrimeRepository.kh_get()
    val kh_crimeListLiveData = kh_crimeRepository.kh_getCrimes()
}