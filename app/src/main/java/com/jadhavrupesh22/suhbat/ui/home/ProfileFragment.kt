package com.jadhavrupesh22.suhbat.ui.home

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.fragment.app.Fragment
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.auth.FirebaseAuth
import com.jadhavrupesh22.suhbat.R
import com.jadhavrupesh22.suhbat.databinding.FragmentProfileBinding
import com.jadhavrupesh22.suhbat.model.User
import com.jadhavrupesh22.suhbat.utils.GetTimeAgo
import com.jadhavrupesh22.suhbat.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import javax.inject.Inject


@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile) {

    lateinit var fav: MenuItem

    @Inject
    lateinit var mAuth: FirebaseAuth

    lateinit var binding: FragmentProfileBinding
    private var fragmentEditProfileBinding: FragmentProfileBinding? = null
    lateinit var homeViewModel: HomeViewModel
    lateinit var user: User
    private lateinit var dataStore: DataStore<Preferences>


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentProfileBinding.bind(view)
        fragmentEditProfileBinding = binding
        dataStore = requireActivity().createDataStore(name = "settings")
        homeViewModel = ViewModelProvider(requireActivity()).get(HomeViewModel::class.java)
        user = homeViewModel.userDetails.value!!
        val toolbar = requireActivity().findViewById<View>(R.id.toolbar) as Toolbar
        toolbar.subtitle = null
        setHasOptionsMenu(true)




        if (user.profileUrl.equals("")) {
            binding.userProfile.visibility = View.INVISIBLE
            binding.addIcon.visibility = View.INVISIBLE
            binding.addImg.visibility = View.VISIBLE
        } else {
            binding.userProfile.visibility = View.VISIBLE
            binding.addIcon.visibility = View.VISIBLE
            binding.addImg.visibility = View.INVISIBLE

            val requestOptions = RequestOptions()
            requestOptions.placeholder(R.drawable.user)
            requestOptions.error(R.drawable.user)
            Glide.with(requireContext())
                .load(user.profileUrl)
                .apply(requestOptions)
                .into(binding.userProfile)
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


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        fav = menu.findItem(R.id.profile).setVisible(false)
        fav = menu.findItem(R.id.viewProfile).setVisible(false)
    }


}