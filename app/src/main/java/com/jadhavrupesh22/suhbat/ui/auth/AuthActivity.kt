package com.jadhavrupesh22.suhbat.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.jadhavrupesh22.suhbat.databinding.ActivityAuthBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {

    lateinit var binding: ActivityAuthBinding

    @Inject
  lateinit var mAuth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_auth)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)








    }
}