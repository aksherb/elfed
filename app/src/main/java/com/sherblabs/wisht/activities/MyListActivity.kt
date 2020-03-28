package com.sherblabs.wisht.activities

import android.app.AlertDialog
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import com.sherblabs.wisht.R

const val LIST_KEY = "test_firebase_list"

class MyListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_list)

        initializeListViewRefresher()
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
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference(LIST_KEY).push()
        myRef.setValue(value)
    }

    private fun initializeListViewRefresher(){
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference(LIST_KEY)
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val map = dataSnapshot.getValue(object : GenericTypeIndicator<Map<String, String>>() {})
                if (map != null) {
                    refreshListView(map.values.toList())
                } else {
                    Log.w(MyListActivity::class.java.toString(), "Map is null.")
                }
            }
            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(MyListActivity::class.java.toString(), "Failed to read value.", error.toException())
            }
        })
    }

    private fun refreshListView(list: List<String>) {
        val adapter = ArrayAdapter(this, R.layout.list_item, list)
        val listView = findViewById<ListView>(R.id.listView)
        listView.adapter = adapter
    }
}
