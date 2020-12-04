package com.jadhavrupesh22.suhbat.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import com.google.firebase.auth.FirebaseAuth
import com.jadhavrupesh22.suhbat.databinding.ActivityHomeBinding
import com.jadhavrupesh22.suhbat.ui.auth.AuthActivity
import dagger.hilt.android.AndroidEntryPoint


import androidx.datastore.preferences.createDataStore
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    lateinit var mAuth: FirebaseAuth
    lateinit var binding: ActivityHomeBinding
    private lateinit var dataStore: DataStore<Preferences>

    private val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        mAuth = FirebaseAuth.getInstance()
//        if (mAuth.currentUser == null) {
//            val intent = Intent(this@HomeActivity, AuthActivity::class.java)
//            startActivity(intent)
//            finish()
//        }
    }

    override fun onStart() {
        super.onStart()
        lifecycleScope.launchWhenStarted {
            val isLogin = read("isLogin")
            if (isLogin != true) {
                val intent = Intent(this@HomeActivity, AuthActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    private suspend fun read(key: String): Boolean? {
        val dataStoreKey = preferencesKey<Boolean>(key)
        val preferences = dataStore.data.first()
        return preferences[dataStoreKey]
    }

}