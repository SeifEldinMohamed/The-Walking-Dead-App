package com.seif.thewalkingdeadapp.data.mapper

import com.seif.thewalkingdeadapp.data.local.entities.CharacterEntity
import com.seif.thewalkingdeadapp.data.local.entities.CharacterRemoteKeysEntity
import com.seif.thewalkingdeadapp.data.remote.dto.CharacterDto

fun CharacterEntity.toCharacterDto(): CharacterDto {
    return CharacterDto(
        id = id,
        name = name,
        realName = realName,
        about = about,
        totalAppearances = totalAppearances,
        image = image,
        quote = quote,
        quoteTime = quoteTime,
        rating = rating
    )
}

fun CharacterDto.toCharacter(): CharacterEntity {
    return CharacterEntity(
        id = id,
        name = name,
        realName = realName,
        about = about,
        totalAppearances = totalAppearances,
        image = image,
        quote = quote,
        quoteTime = quoteTime,
        rating = rating
    )
}

fun CharacterDto.toCharacterRemoteKeys(prevPage: Int?, nextPage: Int?): CharacterRemoteKeysEntity {
    return CharacterRemoteKeysEntity(
        id = id,
        prevPage = prevPage,
        nextPage = nextPage
    )
}