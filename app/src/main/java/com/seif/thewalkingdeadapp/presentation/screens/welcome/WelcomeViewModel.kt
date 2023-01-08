package com.seif.thewalkingdeadapp.presentation.screens.welcome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seif.thewalkingdeadapp.domain.usecase.SaveOnBoardingUseCase
import com.seif.thewalkingdeadapp.domain.usecase.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(
    private val saveOnBoardingUseCase: SaveOnBoardingUseCase
) : ViewModel() {

    fun saveOnBoardingState(completed: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            saveOnBoardingUseCase(completed = completed)
        }
    }

}