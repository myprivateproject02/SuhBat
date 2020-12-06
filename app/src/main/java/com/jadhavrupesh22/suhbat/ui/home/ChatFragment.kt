package com.jadhavrupesh22.suhbat.ui.home

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.onNavDestinationSelected
import com.jadhavrupesh22.suhbat.NavHomeDirections
import com.jadhavrupesh22.suhbat.R
import com.jadhavrupesh22.suhbat.databinding.FragmentChatBinding
import com.jadhavrupesh22.suhbat.model.User
import com.jadhavrupesh22.suhbat.utils.GetTimeAgo

class ChatFragment : Fragment(R.layout.fragment_chat) {


    lateinit var fav: MenuItem
    private val args: ChatFragmentArgs by navArgs()
    lateinit var binding: FragmentChatBinding
    private var fragmentChatFragment: FragmentChatBinding? = null
    lateinit var user: User

    lateinit var navController: NavController


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentChatBinding.bind(view)
        fragmentChatFragment = binding
        val toolbar = requireActivity().findViewById<View>(R.id.toolbar) as Toolbar
        user = args.user
        val time = GetTimeAgo.getTimeAgo(user.lastSeen.seconds)
        toolbar.subtitle = time
        setHasOptionsMenu(true)
        val navHomeFragment = requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHomeFragment.findNavController()


    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        fav = menu.findItem(R.id.profile).setVisible(false)
        fav = menu.findItem(R.id.viewProfile).setVisible(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.viewProfile) {
            val action = NavHomeDirections.actionGlobalViewProfileFragment(user.username, user)
            navController.navigate(action)
            true
        } else {
            item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
        }

    }

}