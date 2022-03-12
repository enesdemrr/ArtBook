package com.project.artbook.repo

import androidx.lifecycle.LiveData
import com.project.artbook.model.ImageResponse
import com.project.artbook.roomdb.Art
import com.project.artbook.util.Resource

interface ArtRepositoryInterface {

    suspend fun insertArt(art : Art)
    suspend fun deleteArt(art: Art)
    fun getArt() : LiveData<List<Art>>
    suspend fun searchImage(imageString: String) : Resource<ImageResponse>
}