package com.sherblabs.wisht.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.sherblabs.wisht.R
import kotlinx.android.synthetic.main.dialog_add_exchange.*

class MyExchangeActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /* Create an ExchangeViewModel the first time the system calls this activity's
         * onCreate() method.
         * Recreated activities receive the same MyExchangeViewModel instance created
         * by the first activity
         */
        val model: MyExchangeViewModel by viewModels()
        model.getExchanges().observe(this, Observer<List<Exchange>>{ exchanges ->
            // update the UI.
        })

        // set content view for top level exchanges.
        setContentView(R.layout.activity_my_exchanges)
    }

    /*
     * Window Activity Callbacks
     */

    fun onClickAddExchange(view: View) {
        val fragmentManager = supportFragmentManager
        val newFragment = ExchangeFragment()
        val transaction = fragmentManager.beginTransaction()

        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        // to make it fullscreen, use the 'content' root view as the container
        // for the fragment, which is always the root view for the activity.
        transaction
            .replace(android.R.id.content, newFragment)
            .addToBackStack(null)
            .commit()
    }
}

class ExchangeFragment : Fragment() {

    private val model: MyExchangeViewModel by activityViewModels()

    // Called when Fragment should create its View object hierarchy.
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_add_exchange, container, false)
    }

    // This event is triggered soon after onCreateView()
    // Any view setup should occur here. eg. attaching listeners.
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.okAddExchange?.setOnClickListener {
            val name = addName.text.toString()
        }
        activity?.cancelAddExchange?.setOnClickListener {
            // update the UI.
        }

    }
}