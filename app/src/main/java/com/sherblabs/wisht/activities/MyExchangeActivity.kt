package com.sherblabs.wisht.activities

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.*
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sherblabs.wisht.R
import com.sherblabs.wisht.models.Exchange
import com.sherblabs.wisht.models.MyExchangeViewModel
import kotlinx.android.synthetic.main.activity_my_exchanges.*
import kotlinx.android.synthetic.main.dialog_add_exchange.*
import kotlinx.android.synthetic.main.list_item.view.*

class MyExchangeActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: ExchangeAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // set content view for top level exchanges.
        setContentView(R.layout.activity_my_exchanges)

        val myExchanges: ArrayList<Exchange> = ArrayList()

        viewManager = LinearLayoutManager(this)
        viewAdapter = ExchangeAdapter(myExchanges, this)
        recyclerView = exchangeRecycler.apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            layoutManager = viewManager
            adapter = viewAdapter
        }

        /* Create an ExchangeViewModel the first time the system calls this activity's
         * onCreate() method.
         * Recreated activities receive the same MyExchangeViewModel instance created
         * by the first activity
         */
        val model: MyExchangeViewModel by viewModels()
        val liveData = model.getObservableExchanges("ALLISON")
        liveData.observe(this, Observer {
            // add items to list.
            viewAdapter.setExchangesData(it)
        })
    }

    /*
     * Window Activity Callbacks
     */

    fun onClickAddExchange(view: View) {
        val fragmentManager = supportFragmentManager
        val newFragment = ExchangeFragment()
        newFragment.show(fragmentManager, "exchangeFragment")
        /*val transaction = fragmentManager.beginTransaction()

        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        // to make it fullscreen, use the 'content' root view as the container
        // for the fragment, which is always the root view for the activity.
        transaction
            .replace(R.id.exchangesView, newFragment, "exchangeFragment")
            .addToBackStack("exchangeFragment")
            .commit()*/
    }
}

class ExchangeAdapter(val items: ArrayList<Exchange>, val context: Context)
    : RecyclerView.Adapter<ViewHolder>() {

    // inflates the item view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item, parent, false))
    }

    // binds each item in the list to a view and sets the text to the exchange name.
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemType.text = items[position].name
    }

    // returns the number of items in the list
    override fun getItemCount(): Int {
        return items.size
    }

    fun setExchangesData(items: List<Exchange>) {
        Log.d("ExchangeAdapter", "setExchangesData called!")
    }
}

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    // holds the textView for each exchange.
    val itemType: TextView = view.textView
}

/*
 * Fragment that allows user provided input on a new Exchange.
 * Uses entire parent view space.
 */
class ExchangeFragment : DialogFragment() {
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
            model.addNewExchange(Exchange(name))

            fragmentManager?.beginTransaction()?.remove(this)?.commit()
        }
    }
}