package com.example.notes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.notes.databinding.FragmentNoteBinding
import com.example.notes.models.NotesRequest
import com.example.notes.models.NotesResponse
import com.example.notes.utils.NetworkResult
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class noteFragment : Fragment() {
    private  var _binding: FragmentNoteBinding?=null
    private val  binding get()=_binding!!
    private var note: NotesResponse?=null
    private val NotesViewModel by viewModels<NotesViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding= FragmentNoteBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setintialData()
        bindHandlers()
        bindObservers()
    }

    private fun bindObservers() {
        NotesViewModel.statusLiveData.observe(viewLifecycleOwner, {
            when (it) {
                is NetworkResult.Success -> {
                    findNavController().popBackStack()
                }

                is NetworkResult.Error -> {

                }

                is NetworkResult.Loading -> {
                }
            }
        })
    }

    private fun bindHandlers() {
      binding.btnDelete.setOnClickListener{
          note?.let{
              NotesViewModel.deleteNotes(it!!._id)
          }
      }
        binding.btnSubmit.setOnClickListener {
            val title=binding.txtTitle.text.toString()
            val description=binding.txtDescription.text.toString()
            val noteRequest= NotesRequest(description, title)
            if(note==null){
                NotesViewModel.createNotes(noteRequest)
            }else{
                NotesViewModel.updateNotes(noteRequest,note!!._id)
            }

        }
    }

    private fun setintialData() {
        val jsonNote= arguments?.getString("note")
        if(jsonNote!=null){
            note= Gson().fromJson(jsonNote, NotesResponse::class.java)
            note?.let{
                binding.txtTitle.setText(it.title)
                binding.txtDescription.setText(it.description)
            }

        }else{
            binding.addEditText.text="Add Note"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

}