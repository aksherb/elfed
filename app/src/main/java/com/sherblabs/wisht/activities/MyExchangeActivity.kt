package com.sherblabs.wisht.activities

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import com.sherblabs.wisht.R

class MyExchangeActivity : FragmentActivity(),
        ExchangeDialogFragment.ExchangeDialogListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_my_exchanges)
    }

    /*
     * Window Activity Callbacks
     */

    fun onClickAddExchange(view: View) {
        val newFragment = ExchangeDialogFragment()
        newFragment.show(supportFragmentManager, "exchanges")
    }

    // fragment interface method implementation
    override fun onDialogPositiveClick(dialog: DialogFragment) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    // fragment interface method implementation
    override fun onDialogNegativeClick(dialog: DialogFragment) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}

class ExchangeDialogFragment : DialogFragment() {
    // Use this instance of the interface to deliver action events
    internal lateinit var listener: ExchangeDialogListener

    /*
     * The activity that creates an instance of this dialog fragment
     * must implement this interface to receive event callbacks.
     */
    interface ExchangeDialogListener {
        fun onDialogPositiveClick(dialog: DialogFragment)
        fun onDialogNegativeClick(dialog: DialogFragment)
    }

    // override to instantiate the listener
    override fun onAttach(context: Context) {
        super.onAttach(context)

        // verify the host activity implemented the interface
        try {
            listener = context as ExchangeDialogListener
        } catch (e: ClassCastException) {
            // if the activity didn't implement the interface throw exception
            throw ClassCastException((context.toString() +
                    " must implement ExchangeDialogListener"))
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_add_exchange, container, false)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater

            builder.setView(inflater.inflate(R.layout.dialog_add_exchange, null))
                .setPositiveButton(android.R.string.ok,
                    DialogInterface.OnClickListener { dialog, id ->
                        listener.onDialogPositiveClick(this)
                    })
                .setNegativeButton(android.R.string.cancel,
                    DialogInterface.OnClickListener { dialog, id ->
                        listener.onDialogNegativeClick(this)
                    })

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be NULL")
    }
}