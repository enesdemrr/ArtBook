package com.project.artbook.view.fragment

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.bumptech.glide.RequestManager
import com.project.artbook.adapter.ArtRecylerAdapter
import com.project.artbook.adapter.ImageRecylerAdapter
import javax.inject.Inject

class ArtFragmentFactory @Inject constructor(
    private val artRecylerAdapter: ArtRecylerAdapter,
    private val imageRecylerAdapter: ImageRecylerAdapter,
    private val glide : RequestManager
) : FragmentFactory(){
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when(className){
            ArtsFragment::class.java.name -> ArtsFragment(artRecylerAdapter)
            ArtDetailsFragment::class.java.name -> ArtDetailsFragment(glide)
            ArtImageApiFragment::class.java.name -> ArtImageApiFragment(imageRecylerAdapter)
            else -> super.instantiate(classLoader, className)
        }
    }
}