package com.example.notes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.notes.databinding.FragmentLoginBinding
import com.example.notes.databinding.FragmentRegisterBinding

class loginFragment : Fragment() {


private var _binding : FragmentLoginBinding?=null
    private  val binding get()=_binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding= FragmentLoginBinding.inflate(inflater,container,false)
        binding.btnLogin.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
        }
        return binding.root


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

}