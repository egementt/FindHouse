package com.example.findhouse.view

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.findhouse.R
import com.example.findhouse.databinding.FragmentProfileBinding
import com.example.findhouse.model.Current
import com.example.findhouse.model.Owner
import com.example.findhouse.model.Student
import com.google.firebase.auth.FirebaseAuth


class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(layoutInflater, container, false)

        if (Current.user != null){
            binding.twUserName.text = Current.user!!.name + " " + Current.user!!.surname
            binding.twUserMailAddress.text = Current.user!!.mailAddress
            binding.twUserPhoneNumber.text = Current.user!!.phoneNumber
            when(Current.user){
                is Student -> {
                    binding.twUserProfileUni.text = (Current.user as Student).university.name
                }
                is Owner -> {
                    binding.twProfileUni.visibility = View.GONE
                    binding.twUserProfileUni.visibility = View.GONE
                }
            }
        }

        binding.btnLogout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            Current.user = null
            findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToLoginFragment2())
        }



        return binding.root


    }
}