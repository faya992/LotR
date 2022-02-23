package com.faya992.lotr.domain.models

import com.faya992.lotr.data.models.CharactersRemote


data class CharacterModel(
    val id: String,
    val name: String,
    val race: String,
    val birth: String,
    val death: String,
    val realm: String,
    val gender: String,
    val spouse: String,
    val hair: String,
    val wiki: String
)

fun CharactersRemote.mapToModel(): CharacterModel {

val i = 0

        return CharacterModel(
            id = docs[i]._id, name = docs[i].name, race = docs[i].race, birth = docs[i].birth,
            death = docs[i].death, realm = docs[i].realm, gender = docs[i].gender, spouse = docs[i].spouse,
            hair = docs[i].hair, wiki = docs[i].wikiUrl
        )

}
