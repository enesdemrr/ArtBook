package com.project.artbook.view.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.RequestManager
import com.project.artbook.adapter.ImageRecylerAdapter
import com.project.artbook.databinding.FragmentArtDetailsBinding
import com.project.artbook.databinding.FragmentImageApiBinding
import com.project.artbook.util.Status
import com.project.artbook.viewmodel.ArtViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ArtImageApiFragment @Inject constructor(
    private val imageRecylerAdapter: ImageRecylerAdapter
): Fragment() {
    private var _binding : FragmentImageApiBinding? = null
    private val binding get() = _binding!!

    lateinit var viewModel: ArtViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentImageApiBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(ArtViewModel::class.java)
        subscribeToObserver()
        var job : Job ?= null
        binding.searchText.addTextChangedListener {
            job?.cancel()
            job = lifecycleScope.launch {
                delay(1000)
                it?.let {
                    if (it.toString().isNotEmpty()){
                        viewModel.searchForImage(it.toString())
                    }
                }
            }
        }



        binding.imageRecylerView.adapter = imageRecylerAdapter
        binding.imageRecylerView.layoutManager = GridLayoutManager(requireContext(),3)
        imageRecylerAdapter.serOnItemClickListener {
            findNavController().popBackStack()
            viewModel.setSelectedImage(it)
        }

    }
    fun subscribeToObserver(){
        viewModel.imageList.observe(viewLifecycleOwner, Observer {
            when(it.status){
                Status.SUCCESS ->{
                    val urls = it.data?.hits?.map { imageResult ->
                        imageResult.previewURL
                    }
                    imageRecylerAdapter.images = urls as List<String> ?: listOf()
                    binding.progressBar.visibility = View.GONE
                }
                Status.ERROR ->{
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    binding.progressBar.visibility = View.GONE

                }
                Status.LOADING ->{
                    binding.progressBar.visibility = View.VISIBLE

                }
            }
        })
    }
}