package com.sherblabs.wisht.activities

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class MyExchangeViewModel : ViewModel() {
    private val exchanges: MutableLiveData<List<Exchange>> by lazy {
        MutableLiveData<List<Exchange>>().also {
            loadExchanges()
        }
    }

    private val dbReference = FirebaseDatabase.getInstance()

    private fun loadExchanges() {
        // Do an asynchronous operation to fetch exchanges
    }

    fun getExchanges(): LiveData<List<Exchange>> {
        return exchanges
    }
}