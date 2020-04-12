package com.sherblabs.wisht.activities

import android.accounts.AccountManager
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import com.sherblabs.wisht.R
import org.apache.commons.codec.binary.Hex
import org.apache.commons.codec.digest.DigestUtils

const val CURRENT_USERNAME_KEY = "current_user"
const val DEFAULT_USERNAME = "default_user"

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        // test comment.
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupUser()
    }

    fun viewMyList(view: View) {
        // second test comment.
        val intent = Intent(this, MyListActivity::class.java)
        intent.putExtra(CURRENT_USERNAME_KEY, getCurrentUsername())
        startActivity(intent)
    }

    fun viewMyExchanges(view: View) {
        val intent = Intent(this, MyExchangeActivity::class.java)
        startActivity(intent)
    }

    private fun setupUser() {
        if (!currentUserIsSet()) {
            askForUserAccount()
        }
    }

    private fun askForUserAccount() {
        val intent = AccountManager.newChooseAccountIntent(
            null,
            null,
            arrayOf("com.google"),
            false,
            null,
            null,
            null,
            null)
        startActivityForResult(intent, 10)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 10) {
            val accountName = data?.extras?.get(AccountManager.KEY_ACCOUNT_NAME)
            if (accountName != null) {
                setCurrentUsername(accountName.toString())
            } else {
                setCurrentUsername(DEFAULT_USERNAME)
            }
        }
    }

    private fun currentUserIsSet(): Boolean {
        return getCurrentUsername() != null
    }

    private fun getCurrentUsername(): String? {
        return getSharedPreferences().getString(CURRENT_USERNAME_KEY, null)
    }

    private fun setCurrentUsername(username: String) {
        getSharedPreferences().edit().putString(CURRENT_USERNAME_KEY,
            getFirebaseKeyForUsername(username)).apply()
    }

    private fun getSharedPreferences(): SharedPreferences {
        return getSharedPreferences(
            "com.sherblabs.wisht", Context.MODE_PRIVATE
        )
    }

    private fun getFirebaseKeyForUsername(username: String): String {
        return String(Hex.encodeHex(DigestUtils.sha1(username)))
    }
}
