package com.bignerdranch.android.criminalintent

import java.util.*

data class KHCrime(val kh_id: UUID = UUID.randomUUID(),
                   var kh_title: String = "",
                   var kh_date: Date = Date(),
                   var kh_isSolved: Boolean = false)