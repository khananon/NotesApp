package com.example.notes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.notes.databinding.FragmentRegisterBinding
import com.example.notes.models.UserRequest
import com.example.notes.utils.NetworkResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class registerFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get()=_binding!!
    private val authViewModel by viewModels<AuthViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)

        binding.btnSignUp.setOnClickListener{
//            findNavController().navigate(R.id.action_registerFragment_to_mainFragment)
            authViewModel.registerUser(UserRequest("test01@gmail.com","test123","test"))
        }
        binding.btnLogin.setOnClickListener{
//            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
            authViewModel.loginUser(UserRequest("test01@gmail.com","test123","test"))
        }
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        authViewModel.userResponseLiveData.observe(viewLifecycleOwner,{
            binding.progressBar.isVisible=false
            when(it){
                is NetworkResult.Success->{
                    findNavController().navigate(R.id.action_registerFragment_to_mainFragment)
                }
                is NetworkResult.Error->{
                    binding.txtError.text=it.message
                }
                is NetworkResult.Loading->{
                    binding.progressBar.isVisible=true
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()//we use this to avoid memory leaks and to destroy with view , without view there is no binding
        _binding = null
    }


}