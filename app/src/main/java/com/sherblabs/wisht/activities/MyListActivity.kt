package com.sherblabs.wisht.activities

import android.app.AlertDialog
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.sherblabs.wisht.R
import com.sherblabs.wisht.controllers.WishListController

class MyListActivity : AppCompatActivity() {

    private lateinit var wishList: WishListController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_list)
        setSupportActionBar(findViewById(R.id.my_toolbar))

        val username = intent.getStringExtra(CURRENT_USERNAME_KEY) ?: DEFAULT_USERNAME
        wishList = WishListController(username, ::refreshListView)
    }

    fun onClickAddItem(view: View) {
        showAddItemPopUp()
    }

    private fun showAddItemPopUp() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle("Item Name")

        val input = EditText(this)
        input.inputType = InputType.TYPE_CLASS_TEXT
        builder.setView(input)

        builder.setPositiveButton("OK") { _, _ -> addItemToList(input.text.toString()) }
        builder.setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }

        builder.show()
    }

    private fun addItemToList(value: String) {
        wishList.add(value)
    }

    private fun refreshListView(list: List<String>) {
        val adapter = ArrayAdapter(this, R.layout.list_item, list)
        val listView = findViewById<ListView>(R.id.listView)
        listView.adapter = adapter
    }
}
