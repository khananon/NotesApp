package com.example.notes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.notes.databinding.FragmentLoginBinding
import com.example.notes.databinding.FragmentRegisterBinding
import com.example.notes.models.UserRequest
import com.example.notes.utils.NetworkResult
import com.example.notes.utils.TokenManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class loginFragment : Fragment() {


private var _binding : FragmentLoginBinding?=null
    private  val binding get()=_binding!!
    lateinit var  tokenManger: TokenManager
    private val authViewModel by viewModels<AuthViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding= FragmentLoginBinding.inflate(inflater,container,false)

        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnLogin.setOnClickListener {
            val validationResult = validateUserInput()
            if (validationResult.first) {
                authViewModel.loginUser(getUserRequest())
            } else {
                binding.txtError.text = validationResult.second
            }

        }
        binding.btnSignUp.setOnClickListener {
            findNavController().popBackStack()
        }
        bindObserver()

    }
        private fun getUserRequest(): UserRequest{
            val emailAddress=binding.txtEmail.text.toString()
            val password=binding.txtPassword.text.toString()
            return UserRequest(emailAddress,password,"")
        }

        private fun validateUserInput(): Pair<Boolean, String> {
            val userRequest=getUserRequest()
           return authViewModel.validateCredentials(
               userRequest.username, userRequest.email, userRequest.password,
               isLogin = true
           )
        }

        private fun bindObserver(){
           authViewModel.userResponseLiveData.observe(viewLifecycleOwner,{
         when(it) {
             is NetworkResult.Success -> {
                 tokenManger.saveToken(it.data!!.token)
               findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
             }

             is NetworkResult.Error -> {
                 binding.txtError.text=it.message
             }


             is NetworkResult.Loading -> {
                 binding.progressBar.isVisible=true

             }
           }

           })
        }



             override fun onDestroyView () {
                 super.onDestroyView()
                 _binding = null
             }

         }