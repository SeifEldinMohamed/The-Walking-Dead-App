package com.seif.thewalkingdeadapp.domain.usecase

import com.seif.thewalkingdeadapp.domain.repository.MainRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReadOnBoardingUseCase @Inject constructor(
    private val mainRepository: MainRepository
) {
    operator fun invoke(): Flow<Boolean> {
        return mainRepository.readOnBoardingState()
    }
}