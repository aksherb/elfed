package com.sherblabs.wisht.activities

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.sherblabs.wisht.R

class MyListActivity : AppCompatActivity() {

    private var dummyList = listOf<String>("Item1", "Item2", "Item3", "Item4")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_list)

        val adapter = ArrayAdapter(this, R.layout.list_item, getList())
        val listView = findViewById<ListView>(R.id.listView)
        listView.adapter = adapter
    }

    fun addItem(view: View) {
        inputPopUp()
    }

    private fun inputPopUp() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle("Item Name")

        val input = EditText(this)
        input.inputType = InputType.TYPE_CLASS_TEXT
        builder.setView(input)

        builder.setPositiveButton("OK",
            DialogInterface.OnClickListener { _, _ ->
                val updatedList = getList() + listOf<String>(input.text.toString())
                updateList(updatedList)
                refreshListView(updatedList)
            })
        builder.setNegativeButton("Cancel",
            DialogInterface.OnClickListener { dialog, _ -> dialog.cancel() })

        builder.show()
    }

    private fun getList(): List<String> {
        // TODO: get list from persistent store
        return dummyList
    }

    private fun updateList(list: List<String>) {
        // TODO: update list in persistent store
    }

    private fun refreshListView(list: List<String>) {
        val adapter = ArrayAdapter(this, R.layout.list_item, list)
        val listView = findViewById<ListView>(R.id.listView)
        listView.adapter = adapter
    }
}
