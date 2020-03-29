package com.sherblabs.wisht.controllers

import android.util.Log
import com.google.firebase.database.*
import com.sherblabs.wisht.activities.MyListActivity

class WishListController(
    username: String,
    private val onDataChangeCallback: (List<String>) -> Unit) {

    private var dbList = FirebaseDatabase.getInstance().reference.child(username)

    init {
        onDataChange()
    }

    fun add(value: String) {
        val myRef = dbList.push()
        myRef.setValue(value)
    }

    private fun onDataChange() {
        dbList.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val map = dataSnapshot.getValue(object : GenericTypeIndicator<Map<String, String>>() {})
                if (map != null) {
                    onDataChangeCallback(map.values.toList())
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
}