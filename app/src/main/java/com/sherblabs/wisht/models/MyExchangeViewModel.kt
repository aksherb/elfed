package com.sherblabs.wisht.models

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore

class MyExchangeViewModel : ViewModel() {
    private val TAG = "MyExchangeViewModel"
    private val repository = FSExchangeRepository.getInstance()

    fun getObservableExchanges(name: String) : LiveData<List<Exchange>> {
       return repository.getExchanges(name)
    }

    fun addNewExchange(exchange: Exchange) {
        repository.addExchange(exchange)
    }
}

interface ExchangeRepository {
    fun getExchanges(name: String): MutableLiveData<List<Exchange>>
    fun addExchange(exchange: Exchange)
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

    override fun addExchange(exchange: Exchange) {
        fsdb.collection("exchanges").document(exchange.name)
            .set(exchange)
            .addOnSuccessListener { Log.d(TAG, "Document successfully written") }
            .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
    }

    fun getInstance() : FSExchangeRepository {
        return this
    }
}