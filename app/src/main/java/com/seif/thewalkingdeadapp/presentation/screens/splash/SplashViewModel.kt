package com.seif.thewalkingdeadapp.presentation.screens.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seif.thewalkingdeadapp.domain.usecase.ReadOnBoardingUseCase
import com.seif.thewalkingdeadapp.domain.usecase.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val readOnBoardingUseCase: ReadOnBoardingUseCase
): ViewModel() {

    private val _onBoardingCompleted = MutableStateFlow(false)
    val onBoardingCompleted: StateFlow<Boolean> = _onBoardingCompleted

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _onBoardingCompleted.value =
                readOnBoardingUseCase().stateIn(viewModelScope).value
        }
    }
}
// stateIn(): Starts the upstream flow in a given scope, suspends until the first value is emitted,
// and returns a hot StateFlow of future emissions