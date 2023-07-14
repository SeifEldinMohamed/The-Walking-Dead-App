package com.seif.thewalkingdeadapp.domain.model

data class CharacterDomainModel(
    val id: Int,
    val name: String,
    val realName: String,
    val about:String,
    val totalAppearances: Int,
    val image: String,
    val quote: String,
    val quoteTime: String,
    val rating: Double
)
