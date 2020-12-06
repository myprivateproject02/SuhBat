package com.jadhavrupesh22.suhbat.ui.home

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.jadhavrupesh22.suhbat.R
import com.jadhavrupesh22.suhbat.adapter.FriendsAdapter
import com.jadhavrupesh22.suhbat.databinding.FragmentFriendsBinding
import com.jadhavrupesh22.suhbat.model.User
import com.jadhavrupesh22.suhbat.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator
import javax.inject.Inject

@AndroidEntryPoint
class FriendsFragment : Fragment(R.layout.fragment_friends), FriendsAdapter.OnItemClickListener {

    lateinit var fav: MenuItem

    lateinit var binding: FragmentFriendsBinding
    private var fragmentFriendsBinding: FragmentFriendsBinding? = null
    private val TAG = "FriendsFragment"
    private lateinit var dataStore: DataStore<Preferences>
    private lateinit var adapter: FriendsAdapter
    lateinit var homeViewModel: HomeViewModel
    var userList: List<User> = ArrayList()

    @Inject
    lateinit var mAuth: FirebaseAuth


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFriendsBinding.bind(view)
        fragmentFriendsBinding = binding
        homeViewModel = ViewModelProvider(requireActivity()).get(HomeViewModel::class.java)
        val toolbar = requireActivity().findViewById<View>(R.id.toolbar) as Toolbar
        toolbar.subtitle = null

    }

    override fun onStart() {
        super.onStart()
        if (homeViewModel.allUsers.value.isNullOrEmpty()) {
            setupObserver().also {
                binding.noData.visibility = View.VISIBLE
                binding.noDataImg.visibility = View.VISIBLE
            }
        } else {
            setUpRecyclerView(homeViewModel.allUsers.value)
        }

    }

    private fun setupObserver() {
        homeViewModel.allUsers.observe(this.viewLifecycleOwner, Observer { userLists ->
            setUpRecyclerView(userLists).also {
                binding.noData.visibility = View.INVISIBLE
                binding.noDataImg.visibility = View.INVISIBLE
            }
        })
    }

    private fun setUpRecyclerView(user: List<User>?) {
        userList = (user ?: emptyList())
        adapter = FriendsAdapter(userList, requireContext(), this)
        binding.recyclerView.itemAnimator = SlideInLeftAnimator()
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onItemClick(position: Int, user: User) {
        val chat = FriendsFragmentDirections.actionFriendsFragmentToChatFragment(user.username,user)
        findNavController().navigate(chat)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        fav = menu.findItem(R.id.profile).setVisible(true)
        fav = menu.findItem(R.id.viewProfile).setVisible(false)
    }







}