package com.project.artbook.view

import dagger.hilt.android.testing.HiltAndroidTest
import androidx.test.filters.MediumTest;
import dagger.hilt.android.testing.HiltAndroidRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@HiltAndroidTest
@MediumTest
class ArtsFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)
    @Before
    fun setup(){
        hiltRule.inject()
    }

    @Test
    fun TestNavigationFromArtsToArtDetails(){
        launch
    }

}