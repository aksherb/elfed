package com.sherblabs.elfed.activities

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.sherblabs.elfed.R

class MyListActivity : AppCompatActivity() {

    var mobileArray = arrayOf("Item1", "Item2", "Item3", "Item4")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_list)

        val adapter = ArrayAdapter(this, R.layout.list_item, mobileArray)
        val listView = findViewById<ListView>(R.id.listView)
        listView.adapter = adapter
    }
}
