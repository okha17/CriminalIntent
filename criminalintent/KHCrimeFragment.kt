package com.bignerdranch.android.criminalintent

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.fragment.app.Fragment
import java.util.*

private const val ARG_CRIME_ID = "crime_id"
private const val TAG = "CrimeFragment"

// Crime fragment is the controller & presents details and updates those details with user change
// KHCrimeFragment is a subclass of Fragment
class KHCrimeFragment: Fragment() {

    // property for the Crime instance
    private lateinit var Crime: Crime
    private lateinit var kh_titleField: EditText
    private lateinit var kh_dateButton: Button
    private lateinit var kh_solvedCheckBox: CheckBox

    // implementation of Fragment.OnCreate(Bundle?)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Crime = Crime()
        val crimeId: UUID = arguments?.getSerializable(ARG_CRIME_ID) as UUID
        Log.d(TAG, "args bundle crime ID: $crimeId")
        // Eventually, load crime from database
    }

    // configure the fragment’s view & inflate the layout for the fragment’s view and return
    // the inflated View to the hosting activity
    override fun onCreateView(
        inflater: LayoutInflater, // layout ID
        container: ViewGroup?,  // View's parent
        savedInstanceState: Bundle?  // whether to add inflated view to the view's parent
    ): View? {
        // inflate the fragment_crime layout
        val kh_view = inflater.inflate(R.layout.fragment_crime, container, false)

        kh_titleField = kh_view.findViewById(R.id.crime_title) as EditText
        kh_dateButton = kh_view.findViewById(R.id.crime_date) as Button
        kh_solvedCheckBox = kh_view.findViewById(R.id.crime_solved) as CheckBox

        kh_dateButton.apply {
            text = Crime.date.toString()
            isEnabled = false
        }
        return kh_view
    }

    // View state is restored after onCreateView and before on Start
    // When state is restored contents of EditText set to whatever is in crime.title
    override fun onStart() {
        super.onStart()

        val kh_titleWatcher = object : TextWatcher {
            override fun beforeTextChanged(
                sequence: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
               // This space is intentionally left blank
            }

            override fun onTextChanged(
                sequence: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                Crime.title = sequence.toString() //user's input to set crime's title
            }

            override fun afterTextChanged(sequence: Editable?) {
                // This one too
            }
        }
        kh_titleField.addTextChangedListener(kh_titleWatcher)

        kh_solvedCheckBox.apply {
            setOnCheckedChangeListener { _, isChecked ->
                Crime.isSolved = isChecked
            }
        }
    }

    companion object {
        fun newInstance(crimeId: UUID): KHCrimeFragment {
            val args = Bundle().apply {
                putSerializable(ARG_CRIME_ID, crimeId)
            }
            return KHCrimeFragment().apply {
                arguments = args
            }
        }
    }

}