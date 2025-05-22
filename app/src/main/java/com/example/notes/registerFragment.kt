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
import com.example.notes.utils.TokenManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class registerFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private val authViewModel by viewModels<AuthViewModel>()
    @Inject
    lateinit var  tokenManger: TokenManager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)

 if(tokenManger.getToken()!=null){
     findNavController().navigate(R.id.action_registerFragment_to_mainFragment)

 }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.btnSignUp.setOnClickListener {
            val validateResult = validateUserInput()
            if (validateResult.first) {
                authViewModel.registerUser(getUserRequest())
            }else {
                binding.txtError.text=validateResult.second
            }

        }
        binding.btnLogin.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }


            bindObserver()
        }
    private fun getUserRequest(): UserRequest{
        val emailAddress = binding.txtEmail.text.toString()
        val password = binding.txtPassword.text.toString()
        val username = binding.txtUsername.text.toString()
        return UserRequest(emailAddress,password,username)
    }
        private fun validateUserInput(): Pair<Boolean, String> {
           val userRequest= getUserRequest()
            return authViewModel.validateCredentials(
                userRequest.username, userRequest.email, userRequest.password,
                isLogin = false
            )
        }

        private fun bindObserver() {

            authViewModel.userResponseLiveData.observe(viewLifecycleOwner, {
                binding.progressBar.isVisible = false
                when (it) {
                    is NetworkResult.Success -> {
                        tokenManger.saveToken(it.data!!.token)
                        findNavController().navigate(R.id.action_registerFragment_to_mainFragment)
                    }

                    is NetworkResult.Error -> {
                        binding.txtError.text = it.message
                    }

                    is NetworkResult.Loading -> {
                        binding.progressBar.isVisible = true
                    }
                }
            })
        }

    override fun onDestroyView() {
        super.onDestroyView()//we use this to avoid memory leaks and to destroy with view , without view there is no binding
        _binding = null
    }

    }




