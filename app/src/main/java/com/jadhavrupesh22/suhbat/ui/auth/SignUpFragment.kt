package com.jadhavrupesh22.suhbat.ui.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.messaging.FirebaseMessaging
import com.jadhavrupesh22.suhbat.R
import com.jadhavrupesh22.suhbat.databinding.FragmentSignUpBinding
import com.jadhavrupesh22.suhbat.model.User
import com.jadhavrupesh22.suhbat.ui.home.HomeActivity
import com.jadhavrupesh22.suhbat.viewmodel.AuthViewModel


class SignUpFragment : Fragment(R.layout.fragment_sign_up) {

    lateinit var binding: FragmentSignUpBinding
    private var fragmentSignUpBinding: FragmentSignUpBinding? = null
    var thiscontext: Context? = null

    //    val authViewModel: AuthViewModel by viewModels()
    lateinit var authViewModel: AuthViewModel

    private val TAG = "SignUpFragment"

    var mAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        thiscontext = container?.getContext()
        authViewModel = ViewModelProvider(requireActivity()).get(AuthViewModel::class.java)
        return super.onCreateView(inflater, container, savedInstanceState)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        binding = FragmentSignUpBinding.bind(view)
        binding = FragmentSignUpBinding.bind(view)
        fragmentSignUpBinding = binding


        binding.signUpBTN.setOnClickListener { view ->
            val username: String = binding.textInputUserName.getEditText()?.text.toString()
            val emaiId: String = binding.textInputEmail.getEditText()?.text.toString()
            val password: String = binding.textInputPassword.getEditText()?.text.toString()
            val confirmPassword: String =
                binding.textInputConfirmpassword.getEditText()?.text.toString()

            binding.progressBar.visibility = View.VISIBLE

            if (!username.isEmpty() && !password.isEmpty() && !confirmPassword.isEmpty() && !emaiId.isEmpty()) {
                if (!(!TextUtils.isEmpty(emaiId) && Patterns.EMAIL_ADDRESS.matcher(emaiId)
                        .matches())
                ) {
                    binding.progressBar.visibility = View.INVISIBLE
                    Toast.makeText(
                        requireContext(),
                        "Please Enter Valide EmailId",
                        Toast.LENGTH_SHORT
                    ).show()
                } else if (password.length < 6) {
                    binding.progressBar.visibility = View.INVISIBLE
                    Toast.makeText(
                        requireContext(),
                        "Password Length Must More Than 6 Charachter",
                        Toast.LENGTH_SHORT
                    ).show()
                } else if (!password.equals(confirmPassword)) {
                    binding.progressBar.visibility = View.INVISIBLE
                    Toast.makeText(requireContext(), "Password is Not Match.", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    binding.progressBar.visibility = View.VISIBLE
                    val user = User(username = username, password = password, emailId = emaiId)

















                    authViewModel.signUp(user)
                        .observe(this.viewLifecycleOwner, Observer { isRegister ->
                            if (isRegister) {
                                user.uid = mAuth.uid.toString()
                                FirebaseMessaging.getInstance().token
                                    .addOnCompleteListener(OnCompleteListener { task ->
                                        if (!task.isSuccessful) {
                                            return@OnCompleteListener
                                        }
                                        // Get new FCM registration token
                                        val token = task.result
                                        user.tokenId = token.toString()

                                        authViewModel.addUserData(user).observe(
                                            this.viewLifecycleOwner,
                                            Observer { isAdded ->
                                                if (isAdded) {
                                                    binding.progressBar.visibility = View.INVISIBLE

                                                    val home = Intent(
                                                        requireContext(),
                                                        HomeActivity::class.java
                                                    )
                                                    startActivity(home)
                                                    requireActivity().finish()
                                                    Toast.makeText(
                                                        requireContext(),
                                                        "Registration successfull",
                                                        Toast.LENGTH_SHORT
                                                    ).show()
                                                }
                                            })
                                    })

                            } else {
                                binding.progressBar.visibility = View.INVISIBLE
                                Snackbar.make(
                                    view,
                                    "Registration Not successfull",
                                    Snackbar.LENGTH_SHORT
                                ).show()
                            }

                        })
                }
            } else {
                binding.progressBar.visibility = View.INVISIBLE
                Toast.makeText(
                    requireContext(),
                    "Pleas Fill All The Informaton",
                    Toast.LENGTH_SHORT
                ).show()
            }

//            if (!authViewModel.allUsers.value.isNullOrEmpty()) {
//                Snackbar.make(
//                    view,
//                    "List Is Not Empty",
//                    Snackbar.LENGTH_SHORT
//                ).show()
//                authViewModel.allUsers.value?.forEach { user ->
//                    Log.e(TAG, "onViewCreated: " + user.toString())
//                }
//            } else {
//                Snackbar.make(
//                    view,
//                    "List Is Empty",
//                    Snackbar.LENGTH_SHORT
//                ).show()
//            }


        }


//


        binding.signIn.setOnClickListener {
            val action = SignUpFragmentDirections.actionSignUpFragmentToLoginFragment()
            findNavController().navigate(action)

        }


    }


}