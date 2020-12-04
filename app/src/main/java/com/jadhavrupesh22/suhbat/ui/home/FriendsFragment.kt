package com.jadhavrupesh22.suhbat.ui.home

import android.os.Bundle
import android.util.Log
import android.view.OrientationEventListener
import androidx.fragment.app.Fragment
import android.view.View
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.jadhavrupesh22.suhbat.R
import com.jadhavrupesh22.suhbat.adapter.FriendsAdapter
import com.jadhavrupesh22.suhbat.databinding.FragmentFriendsBinding
import com.jadhavrupesh22.suhbat.model.User
import com.jadhavrupesh22.suhbat.viewmodel.AuthViewModel
import com.jadhavrupesh22.suhbat.viewmodel.HomeViewModel
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator
import kotlinx.android.synthetic.main.fragment_friends.*
import timber.log.Timber


class FriendsFragment : Fragment(R.layout.fragment_friends), FriendsAdapter.OnItemClickListener {

    lateinit var binding: FragmentFriendsBinding
    private var fragmentFriendsBinding: FragmentFriendsBinding? = null
    private val TAG = "FriendsFragment"
    private lateinit var dataStore: DataStore<Preferences>
    private lateinit var adapter: FriendsAdapter
    private lateinit var userList: List<User>
    lateinit var homeViewModel: HomeViewModel













    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFriendsBinding.bind(view)
        fragmentFriendsBinding = binding
        homeViewModel = ViewModelProvider(requireActivity()).get(HomeViewModel::class.java)

        homeViewModel.allUsers.observe(this.viewLifecycleOwner, object : Observer<List<User>?> {
            override fun onChanged(t: List<User>?) {
                if (!t.isNullOrEmpty()) {
                    if (t.size < 0) {

                    }
                    setUpRecyclerView(t)
                    binding.noData.visibility = View.INVISIBLE
                    binding.noDataImg.visibility = View.INVISIBLE
                } else {
                    binding.noData.visibility = View.VISIBLE
                    binding.noDataImg.visibility = View.VISIBLE
                }
            }
        })


    }

    private fun setUpRecyclerView(user: List<User>?) {
        userList = user ?: emptyList()
        adapter = FriendsAdapter(userList, requireContext(),this)
        binding.recyclerView.itemAnimator = SlideInLeftAnimator()
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onItemClick(position: Int) {
        view?.let { Snackbar.make(it, "position  = $position", Snackbar.LENGTH_LONG).show() }
        Timber.e("onItemClick: " + position)
    }

}