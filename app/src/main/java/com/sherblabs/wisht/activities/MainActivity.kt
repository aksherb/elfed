package com.sherblabs.wisht.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.sherblabs.wisht.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        // test comment.
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun viewMyList(view: View) {
        // second test comment.
        val intent = Intent(this, MyListActivity::class.java)
        startActivity(intent)
    }
}
