package com.seif.thewalkingdeadapp.data.mapper

import com.seif.thewalkingdeadapp.data.local.entities.Character
import com.seif.thewalkingdeadapp.data.local.entities.CharacterRemoteKeys
import com.seif.thewalkingdeadapp.data.remote.dto.CharacterDto

fun Character.toCharacterDto(): CharacterDto {
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

fun CharacterDto.toCharacter(): Character {
    return Character(
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

fun CharacterDto.toCharacterRemoteKeys(prevPage: Int?, nextPage: Int?): CharacterRemoteKeys {
    return CharacterRemoteKeys(
        id = id,
        prevPage = prevPage,
        nextPage = nextPage
    )
}