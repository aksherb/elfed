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

    var list = listOf<String>("Item1", "Item2", "Item3", "Item4")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_list)

        val adapter = ArrayAdapter(this, R.layout.list_item, list)
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
                updateList(list + listOf<String>(input.text.toString())) })
        builder.setNegativeButton("Cancel",
            DialogInterface.OnClickListener { dialog, _ -> dialog.cancel() })

        builder.show()
    }

    private fun updateList(list: List<String>) {
        val adapter = ArrayAdapter(this, R.layout.list_item, list)
        val listView = findViewById<ListView>(R.id.listView)
        listView.adapter = adapter
    }
}
