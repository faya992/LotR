package com.faya992.lotr.domain.models

import com.faya992.lotr.data.models.QuoteRemote
import com.faya992.lotr.data.room.dao.QuotesListEntity

data class QuoteModel (
    val _id : String,
    val dialog : String,
    val movie : String,
    val character : String,
    val id : String
)

fun QuotesListEntity.mapToModel(): QuoteRemote {
return QuoteRemote(_id = this.id, dialog = this.dialog,
    character = this.character, id = this.name, movie = this.movie)
}

fun QuoteRemote.mapToRoomModel(): QuotesListEntity {
    return QuotesListEntity(id = this._id, dialog = this.dialog,
        character = this.character, name = this.id, movie = this.movie)
}