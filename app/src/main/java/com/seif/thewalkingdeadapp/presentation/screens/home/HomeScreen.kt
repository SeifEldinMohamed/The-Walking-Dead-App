package com.seif.thewalkingdeadapp.presentation.screens.home

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import coil.annotation.ExperimentalCoilApi

@ExperimentalCoilApi
@Composable
fun HomeScreen() {

    Scaffold(
        topBar = {
            HomeTopBar(onSearchClicked = {})
        },
        content = {}
    )
}