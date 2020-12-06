package com.jadhavrupesh22.suhbat.ui.home

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.jadhavrupesh22.suhbat.R
import com.jadhavrupesh22.suhbat.databinding.FragmentViewProfileBinding

class ViewProfileFragment : Fragment(R.layout.fragment_view_profile) {
    lateinit var fav:MenuItem

    lateinit var binding: FragmentViewProfileBinding
    private var fragmentViewProfileBinding: FragmentViewProfileBinding? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentViewProfileBinding.inflate(layoutInflater)
        fragmentViewProfileBinding = binding
        val toolbar = requireActivity().findViewById<View>(R.id.toolbar) as Toolbar
        toolbar.subtitle = null
        setHasOptionsMenu(true)
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        fav = menu.findItem(R.id.profile).setVisible(false)
        fav = menu.findItem(R.id.viewProfile).setVisible(false)
    }




}