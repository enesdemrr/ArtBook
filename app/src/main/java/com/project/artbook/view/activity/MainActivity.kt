package com.project.artbook.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.project.artbook.R
import com.project.artbook.view.fragment.ArtFragmentFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject lateinit var fragmentFactory: ArtFragmentFactory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.fragmentFactory = fragmentFactory
        setContentView(R.layout.activity_main)
    }
}