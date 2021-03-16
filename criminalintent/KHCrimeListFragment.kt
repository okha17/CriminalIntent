package com.bignerdranch.android.criminalintent

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*

private const val TAG = "CrimeListFragment"

class KHCrimeListFragment: Fragment() {

    /*** Required interface for hosting activities*/
    interface KH_Callbacks {
        fun kh_onCrimeSelected(crimeId: UUID)
    }
    private var kh_callbacks: KH_Callbacks? = null

    private lateinit var kh_crimeRecyclerView: RecyclerView
    // initially empty because the fragment will have to wait for results from the database before
    // it can populate the recycler view with crimes,
    private var kh_adapter: CrimeAdapter? = CrimeAdapter(emptyList())

    private val kh_crimeListViewModel: KHCrimeListViewModel by lazy {
        ViewModelProviders.of(this).get(KHCrimeListViewModel::class.java)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        kh_callbacks = context as KH_Callbacks?
    }

    companion object {
        fun newInstance(): KHCrimeListFragment {
            return KHCrimeListFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ):
            View? {
        val kh_view = inflater.inflate(R.layout.fragment_crime_list, container, false)

        kh_crimeRecyclerView =
            kh_view.findViewById(R.id.crime_recycler_view) as RecyclerView
        kh_crimeRecyclerView.layoutManager = LinearLayoutManager(context)

        kh_crimeRecyclerView.adapter = kh_adapter
        return kh_view
    }

    //  used to register an observer on the LiveData instance and tie the life of the observation to
    //  the life of another component
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        kh_crimeListViewModel.kh_crimeListLiveData.observe(
            viewLifecycleOwner,
            Observer { kh_crimes ->
                kh_crimes?.let {
                    Log.i(TAG, "Got crimes ${kh_crimes.size}")
                    kh_updateUI(kh_crimes)
                }
            })
    }

    override fun onDetach() {
        super.onDetach()
        kh_callbacks = null
    }

    private fun kh_updateUI(____crimes: List<Crime>) {
        kh_adapter = CrimeAdapter(____crimes)
        kh_crimeRecyclerView.adapter = kh_adapter
    }

    private inner class CrimeHolder(view: View):
        RecyclerView.ViewHolder(view), View.OnClickListener {

        private lateinit var Crime: Crime

        private val kh_solvedImageView: ImageView = itemView.findViewById(R.id.crime_solved)

        private val titleTextView: TextView = itemView.findViewById(R.id.crime_title)
        private val dateTextView: TextView = itemView.findViewById(R.id.crime_date)

        init {
            itemView.setOnClickListener(this)
        }

        // binding inside your CrimeHolder instead of the adapter
        fun bind(Crime: Crime) {
            this.Crime = Crime
            titleTextView.text = this.Crime.title
            dateTextView.text = this.Crime.date.toString()
            kh_solvedImageView.visibility = if (Crime.isSolved) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }

        // On clicklistener on the itemview which is view for the entire row
        override fun onClick(v: View) {
            kh_callbacks?.kh_onCrimeSelected(Crime.id)
        }
    }

    private inner class CrimeAdapter(var Crimes: List<Crime>)
        : RecyclerView.Adapter<CrimeHolder>() {

        //  responsible for creating a view to display, wrapping the view in a view holder, and returning the result.
        // inflate list_item_crime view
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
                : CrimeHolder {
            val kh_view = layoutInflater.inflate(R.layout.list_item_crime, parent, false)
            return CrimeHolder(kh_view)
        }

        //  responsible for populating a given holder with the crime from a given position
        override fun onBindViewHolder(holder: CrimeHolder, position: Int) {
            val kh_crime = Crimes[position]
            holder.bind(kh_crime)
        }

        // When the recycler view needs to know how many items are in the data set backing it
        // returns the number of items in the list of crimes
        override fun getItemCount() = Crimes.size
    }
}