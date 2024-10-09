package com.github.enaknalla.mediarrtv.ui.screens.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.github.enaknalla.mediarrtv.ui.components.ImmersiveList

@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel()) {
    val data = viewModel.data.collectAsState()

    ImmersiveList(data.value)
}