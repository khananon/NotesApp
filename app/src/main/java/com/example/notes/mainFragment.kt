package com.example.notes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.notes.api.NotesAPi
import com.example.notes.databinding.FragmentMainBinding
import com.example.notes.models.NotesResponse
import com.example.notes.utils.NetworkResult
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class mainFragment : Fragment() {


private var _binding: FragmentMainBinding? = null
    private val notesViewModel by viewModels<NotesViewModel>()
    private val binding get() = _binding!!
    private lateinit var adapter: NoteAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

    _binding= FragmentMainBinding.inflate(inflater,container,false)
  adapter = NoteAdapter(::onNoteClick)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindObserver()
        notesViewModel.getNotes()
        binding.noteList.layoutManager= StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.noteList.adapter=adapter
        binding.addNote.setOnClickListener{
            findNavController().navigate(R.id.action_mainFragment_to_noteFragment)
        }
    }

    private fun bindObserver() {
        notesViewModel.notesLiveData.observe(viewLifecycleOwner,{
            binding.progressBar.isVisible=false
            when(it){

                is NetworkResult.Success ->{
                    adapter.submitList(it.data)

                }

                is NetworkResult.Error<*> -> {
                    Toast.makeText(requireContext(),it.message.toString(),Toast.LENGTH_SHORT).show()

                }
                is NetworkResult.Loading<*> -> {
                    binding.progressBar.isVisible=true
                }
            }
        })

    }
    private fun onNoteClick(notesResponse: NotesResponse){
        val bundel=Bundle()
        bundel.putString("note", Gson().toJson(notesResponse))
        findNavController().navigate(R.id.action_mainFragment_to_noteFragment,bundel)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

}