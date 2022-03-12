package com.project.artbook.repo

import androidx.lifecycle.LiveData
import com.project.artbook.api.RetrofitApi
import com.project.artbook.model.ImageResponse
import com.project.artbook.roomdb.Art
import com.project.artbook.roomdb.ArtDao
import com.project.artbook.util.Resource
import javax.inject.Inject

class ArtRepository @Inject constructor(
    private val artDao : ArtDao,
    private val retrofitApi: RetrofitApi
) : ArtRepositoryInterface  {
    override suspend fun insertArt(art: Art) {
        artDao.insertArt(art)
    }

    override suspend fun deleteArt(art: Art) {
        artDao.deleteArt(art)
    }

    override fun getArt(): LiveData<List<Art>> {
        return artDao.observeArts()
    }

    override suspend fun searchImage(imageString: String): Resource<ImageResponse> {
        return try {
            val resonse = retrofitApi.imageSearch(imageString)
            if (resonse.isSuccessful){
                resonse.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("Error",null)
            }else{
                Resource.error("Error",null)
            }
        }catch (e : Exception){
            Resource.error("No Data", null)
        }
    }
}