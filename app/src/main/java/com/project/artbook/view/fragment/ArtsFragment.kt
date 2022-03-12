package com.project.artbook.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.artbook.adapter.ArtRecylerAdapter
import com.project.artbook.databinding.FragmentArtsBinding
import com.project.artbook.viewmodel.ArtViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class ArtsFragment @Inject constructor(
    private val artRecylerAdapter: ArtRecylerAdapter
): Fragment() {
    private var _binding : FragmentArtsBinding? = null
    private val binding get() = _binding!!
    lateinit var viewModel : ArtViewModel

    private val swipecallBack = object  : ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT){
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val layoutPosition = viewHolder.layoutPosition
            val selectedArt = artRecylerAdapter.arts[layoutPosition]
            viewModel.deleteArt(selectedArt)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentArtsBinding.inflate(inflater,container,false)
        viewModel = ViewModelProvider(requireActivity()).get(ArtViewModel::class.java)
        ItemTouchHelper(swipecallBack).attachToRecyclerView(binding.artsRecylerView)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeToObservers()
        binding.artsRecylerView.adapter = artRecylerAdapter
        binding.artsRecylerView.layoutManager = LinearLayoutManager(requireContext())
        binding.fab.setOnClickListener {
            findNavController().navigate(ArtsFragmentDirections.actionArtsFragmentToArtDetailsFragment())
        }

    }
    private fun subscribeToObservers(){
        viewModel.artList.observe(viewLifecycleOwner, Observer {
            artRecylerAdapter.arts = it
        })
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}