package com.bignerdranch.android.criminalintent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import java.util.*

private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity(),
    KHCrimeListFragment.KH_Callbacks {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val kh_currentFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container)

        if (kh_currentFragment == null) {
            val kh_fragment = KHCrimeListFragment.newInstance()
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, kh_fragment)
                .commit()
        }
    }

    override fun kh_onCrimeSelected(crimeId: UUID) {
        val kh_fragment = KHCrimeFragment.newInstance(crimeId)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, kh_fragment)
            .addToBackStack(null)
            .commit()
    }
}