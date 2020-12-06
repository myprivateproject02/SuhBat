package com.jadhavrupesh22.suhbat.ui.home


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.jadhavrupesh22.suhbat.NavHomeDirections
import com.jadhavrupesh22.suhbat.R
import com.jadhavrupesh22.suhbat.databinding.ActivityHomeBinding
import com.jadhavrupesh22.suhbat.model.User
import com.jadhavrupesh22.suhbat.ui.auth.AuthActivity
import com.jadhavrupesh22.suhbat.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    @Inject
    lateinit var mAuth: FirebaseAuth

    @Inject
    lateinit var userCollection: CollectionReference

    lateinit var binding: ActivityHomeBinding
    private lateinit var dataStore: DataStore<Preferences>
    private val TAG = "MainActivity"
    lateinit var navController: NavController
    lateinit var homeViewModel: HomeViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        CheckAuth()
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        val navHomeFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHomeFragment.findNavController()
        setSupportActionBar(binding.toolbar)
        setupActionBarWithNavController(navController)
        binding.toolbar.setupWithNavController(navController)
        binding.toolbar.overflowIcon = ContextCompat.getDrawable(this, R.drawable.ic_round_wrap_text_24)


    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.profile) {
            val action = NavHomeDirections.actionGlobalProfileFragment()
            navController.navigate(action)
            true
        } else if (item.itemId == R.id.logout) {
            mAuth.signOut()
            lifecycleScope.launch {
                save("isLogin", false)
                val intent = Intent(this@HomeActivity, AuthActivity::class.java)
                startActivity(intent)
                finish()
            }
            true
        } else {
            item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
        }
    }

    private fun CheckAuth() {
        dataStore = createDataStore(name = "settings")
        lifecycleScope.launchWhenStarted {
            val isLogin = read("isLogin")
            if (isLogin == null) {
                Log.e(TAG, "onStart: " + isLogin)
                lifecycleScope.launch { save("isLogin", false) }
                val intent = Intent(this@HomeActivity, AuthActivity::class.java)
                startActivity(intent)
                finish()
            } else if (isLogin == false) {
                val intent = Intent(this@HomeActivity, AuthActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    private suspend fun save(key: String, value: Boolean) {
        val dataStoreKey = preferencesKey<Boolean>(key)
        dataStore.edit { settings ->
            settings[dataStoreKey] = value
        }
    }

    private suspend fun read(key: String): Boolean? {
        val dataStoreKey = preferencesKey<Boolean>(key)
        val preferences = dataStore.data.first()
        return preferences[dataStoreKey]
    }

}