package com.jadhavrupesh22.suhbat.ui.auth

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.jadhavrupesh22.suhbat.R
import com.jadhavrupesh22.suhbat.databinding.FragmentLoginBinding
import com.jadhavrupesh22.suhbat.model.User
import com.jadhavrupesh22.suhbat.ui.home.HomeActivity
import com.jadhavrupesh22.suhbat.viewmodel.AuthViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginFragment : Fragment(R.layout.fragment_login) {

    lateinit var binding: FragmentLoginBinding
    private var fragmentLoginBinding: FragmentLoginBinding? = null
    lateinit var authViewModel: AuthViewModel
    var mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    lateinit var dataStore: DataStore<Preferences>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)
        fragmentLoginBinding = binding
        authViewModel = ViewModelProvider(requireActivity()).get(AuthViewModel::class.java)
        binding.loginBTN.setOnClickListener {
            val emaiId: String = binding.textInputEmail.getEditText()?.text.toString()
            val password: String = binding.textInputPassword.getEditText()?.text.toString()
            if (!emaiId.isEmpty() && !password.isEmpty()) {
                if (!(!TextUtils.isEmpty(emaiId) && Patterns.EMAIL_ADDRESS.matcher(emaiId).matches())) {
                    Toast.makeText(requireContext(), "Please Enter Valide Email Id", Toast.LENGTH_SHORT).show()
                } else {
                    binding.progressBar.visibility = View.VISIBLE
                    val user = User(password = password, emailId = emaiId)
                    authViewModel.signIn(user)
                        .observe(this.viewLifecycleOwner, Observer { isLogin ->
                            if (isLogin) {
                                binding.progressBar.visibility = View.INVISIBLE
                                lifecycleScope.launch {
                                    save("isLogin", true)
                                }.also {
                                    val home = Intent(requireContext(), HomeActivity::class.java)
                                    startActivity(home)
                                    requireActivity().finish()
                                }
                                Toast.makeText(requireContext(), "Login successfull", Toast.LENGTH_SHORT).show()
                            } else {
                                lifecycleScope.launch { save("isLogin", false) }
                                binding.progressBar.visibility = View.INVISIBLE
                                Snackbar.make(
                                    view,
                                    "Login Not successfull, Something Wrong",
                                    Snackbar.LENGTH_SHORT
                                ).show()
                            }
                        })
                }
            } else {
                binding.progressBar.visibility = View.INVISIBLE
                Toast.makeText(
                    requireContext(),
                    "Please Enter The Required Feild.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        binding.signUp.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToSignUpFragment()
            findNavController().navigate(action)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataStore = requireActivity().createDataStore(name = "settings")
    }

    private suspend fun save(key: String, value: Boolean) {
        val dataStoreKey = preferencesKey<Boolean>(key)
        dataStore.edit { settings ->
            settings[dataStoreKey] = value
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        fragmentLoginBinding = null

    }

}