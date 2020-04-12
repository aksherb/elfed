package com.sherblabs.wisht.controllers

import android.util.Log
import com.google.firebase.database.*

class WishListController(
    username: String,
    private val onDataChangeCallback: (List<String>) -> Unit) {

    private var dbList = FirebaseDatabase.getInstance().reference.child(username)

    init {
        onDataChange()
    }

    fun add(value: String) {
        dbList.child(value).setValue(value)
    }

    fun remove(value: String) {
        dbList.child(value).removeValue()
    }

    private fun onDataChange() {
        dbList.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val map = dataSnapshot.getValue(object : GenericTypeIndicator<Map<String, String>>() {})
                onDataChangeCallback(map?.values?.toList().orEmpty())
            }
            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(WishListController::class.java.toString(), "Failed to read value.", error.toException())
            }
        })
    }
}