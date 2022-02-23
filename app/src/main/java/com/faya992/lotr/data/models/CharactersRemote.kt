package com.faya992.lotr.data.models

data class CharactersRemote(
    val docs: List<CharacterRemote>,
    val total: Int, val limit: Int, val offset: Int,
    val page: Int, val pages: Int
)

data class CharacterRemote(
    val _id: String, val height: String, val race: String,
    val gender: String, val birth: String,
    val spouse: String, val death: String,
    val realm: String, val hair: String,
    val name: String, val wikiUrl: String
)
