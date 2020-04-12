package com.sherblabs.wisht.models

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore

class MyExchangeViewModel : ViewModel() {
    private val TAG = "MyExchangeViewModel"
    private val repository = FSExchangeRepository.getInstance()
    private val observableExchanges = MutableLiveData<List<Exchange>>()

    fun getObservableExchanges(name: String) : LiveData<List<Exchange>> {
       return repository.getExchanges(name)
    }
}

interface ExchangeRepository {
    fun getExchanges(name: String): MutableLiveData<List<Exchange>>
}

object FSExchangeRepository : ExchangeRepository {
    private val TAG = "FSExchangeRepository"
    private val fsdb = FirebaseFirestore.getInstance()
    private val data: ArrayList<Exchange> = ArrayList()
    private val exchanges: MutableLiveData<List<Exchange>> by lazy {
        MutableLiveData<List<Exchange>>()
    }

    override fun getExchanges(name: String): MutableLiveData<List<Exchange>> {

        fsdb.collection("exchanges")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                    val item = document.toObject(Exchange::class.java)
                    data.add(item)
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }

        exchanges.value = data
        return exchanges
    }

    fun getInstance() : FSExchangeRepository {
        return this
    }
}