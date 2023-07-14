package com.seif.thewalkingdeadapp.domain.usecase

import androidx.paging.PagingData
import com.seif.thewalkingdeadapp.domain.model.CharacterDomainModel
import com.seif.thewalkingdeadapp.domain.repository.MainRepository
import kotlinx.coroutines.flow.Flow

class GetAllCharactersUseCase(
    private val mainRepository: MainRepository
) {
    operator fun invoke(): Flow<PagingData<CharacterDomainModel>> {
        return mainRepository.getAllCharacters()
    }
}