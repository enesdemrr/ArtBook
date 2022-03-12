package com.project.artbook.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.project.artbook.databinding.ImageRowBinding
import javax.inject.Inject

class ImageRecylerAdapter @Inject constructor(
    val glide : RequestManager
) : RecyclerView.Adapter<ImageRecylerAdapter.ImageViewHolder>(){
    class ImageViewHolder (val bind : ImageRowBinding) : RecyclerView.ViewHolder(bind.root)

    private var onItemClickListener : ((String) -> Unit)? = null
    private val diffUtil  = object : DiffUtil.ItemCallback<String>(){
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return  oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

    }
    private val recyclerListDiffer = AsyncListDiffer(this,diffUtil)

    var images : List<String>
        get() = recyclerListDiffer.currentList
        set(value) = recyclerListDiffer.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val bind = ImageRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ImageViewHolder(bind)
    }

    fun serOnItemClickListener(listener : (String) -> Unit){
        onItemClickListener = listener
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val url = images[position]
        holder.bind.apply {
            glide.load(url).into(singleImageView)
            singleImageView.setOnClickListener {
                onItemClickListener?.let {
                    it(url)
                }
            }


        }

    }

    override fun getItemCount(): Int {
        return images.size
    }

}