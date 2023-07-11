package com.seif.thewalkingdeadapp.data.mapper

import com.seif.thewalkingdeadapp.data.local.entities.CharacterEntity
import com.seif.thewalkingdeadapp.data.local.entities.CharacterRemoteKeysEntity
import com.seif.thewalkingdeadapp.data.remote.dto.CharacterDto
import com.seif.thewalkingdeadapp.domain.model.CharacterDomainModel

fun CharacterDto.toCharacterDomainModel(): CharacterDomainModel {
    return CharacterDomainModel(
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
fun CharacterDto.toCharacterEntity(): CharacterEntity {
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

fun CharacterDto.toCharacterRemoteKeysEntity(prevPage: Int?, nextPage: Int?): CharacterRemoteKeysEntity {
    return CharacterRemoteKeysEntity(
        id = id,
        prevPage = prevPage,
        nextPage = nextPage
    )
}

fun CharacterEntity.toCharacterDomainModel(): CharacterDomainModel {
    return CharacterDomainModel(
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

fun CharacterDomainModel.toCharacterDto(): CharacterDto {
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

fun CharacterDomainModel.toCharacterEntity(): CharacterEntity {
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
