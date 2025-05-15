package com.example.notes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.notes.databinding.FragmentRegisterBinding

class registerFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get()=_binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)

        binding.btnSignUp.setOnClickListener{
            findNavController().navigate(R.id.action_registerFragment_to_mainFragment)
        }
        binding.btnLogin.setOnClickListener{
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
        return  binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()//we use this to avoid memory leaks and to destroy with view , without view there is no binding
        _binding = null
    }


}