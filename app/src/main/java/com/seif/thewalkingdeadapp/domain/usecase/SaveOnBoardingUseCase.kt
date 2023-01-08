package com.seif.thewalkingdeadapp.domain.usecase

import com.seif.thewalkingdeadapp.domain.repository.MainRepository
import javax.inject.Inject

class SaveOnBoardingUseCase @Inject constructor(
    private val mainRepository: MainRepository
) {
    suspend operator fun invoke(completed: Boolean) {
        mainRepository.saveOnBoardingState(completed = completed)
    }
}