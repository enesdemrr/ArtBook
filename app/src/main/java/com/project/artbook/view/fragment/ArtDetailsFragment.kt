package com.project.artbook.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.RequestManager
import com.project.artbook.databinding.FragmentArtDetailsBinding
import com.project.artbook.util.Status
import com.project.artbook.viewmodel.ArtViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
@AndroidEntryPoint
class ArtDetailsFragment @Inject constructor(val glide : RequestManager) : Fragment() {
    private var _binding : FragmentArtDetailsBinding? = null
    private val binding get() = _binding!!
    lateinit var viewModel : ArtViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentArtDetailsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(ArtViewModel::class.java)
        subscribeToObservers()
        binding.imageViewArt.setOnClickListener {
            findNavController().navigate(ArtDetailsFragmentDirections.actionArtDetailsFragmentToArtImageApiFragment())
        }
        val callback = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)
        binding.Savebtn.setOnClickListener {
            viewModel.makeArt(
                binding.nameText.text.toString(),
                binding.artistNameText.text.toString(),
                binding.yearText.text.toString())
        }
    }
    private fun subscribeToObservers(){
        viewModel.insertArtMessage.observe(viewLifecycleOwner, Observer {
            when(it.status){
                Status.SUCCESS ->{
                    Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT).show()
                    findNavController().popBackStack()
                    viewModel.resetInsertArtMsg()
                }
                Status.ERROR ->{
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()

                }
                Status.LOADING ->{

                }
            }
        })
        viewModel.selectedImageUrl.observe(viewLifecycleOwner, Observer { url ->
            binding.let {
                glide.load(url).into(it.imageViewArt)
            }
        })

    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}