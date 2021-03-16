package com.bignerdranch.android.criminalintent

import android.app.Application

class KHCriminalIntentApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        KHCrimeRepository.kh_initialize(this)
    }
}