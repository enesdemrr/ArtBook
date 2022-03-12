package com.project.artbook.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.project.artbook.databinding.ArtRowBinding
import com.project.artbook.roomdb.Art
import javax.inject.Inject

class ArtRecylerAdapter @Inject constructor(
    val glide : RequestManager
) : RecyclerView.Adapter<ArtRecylerAdapter.ArtViewHolder>() {

    class ArtViewHolder(val bind : ArtRowBinding) : RecyclerView.ViewHolder(bind.root)
    private val diffUtil  = object : DiffUtil.ItemCallback<Art>(){
        override fun areItemsTheSame(oldItem: Art, newItem: Art): Boolean {
            return  oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Art, newItem: Art): Boolean {
            return oldItem == newItem
        }

    }
    private val recyclerListDiffer = AsyncListDiffer(this,diffUtil)

    var arts : List<Art>
    get() = recyclerListDiffer.currentList
    set(value) = recyclerListDiffer.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtViewHolder {
        val bind = ArtRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ArtViewHolder(bind)
    }

    override fun onBindViewHolder(holder: ArtViewHolder, position: Int) {
        holder.bind.apply {
            val art = arts[position]
            glide.load(art.imageUrl).into(imgArtRow)
            tvArtRowName.text = art.name
            tvArtistRowName.text = art.artistName
            tvArtRowYear.text = art.year.toString()
        }
    }

    override fun getItemCount(): Int {
       return arts.size
    }
}