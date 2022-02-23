package com.faya992.lotr.domain.models

import com.faya992.lotr.data.models.MovieRemote


data class MovieModel(
    val _id: String, val name: String, val runtimeInMinutes: Int,
    val budgetInMillions: Int, val boxOfficeRevenueInMillions: Double,
    val academyAwardNominations: Int, val academyAwardWins: Int
)

fun MovieRemote.mapToDomain(): MovieModel {


    return MovieModel(
        this._id, this.name, this.runtimeInMinutes,
        this.budgetInMillions, this.boxOfficeRevenueInMillions,
        this.academyAwardNominations, this.academyAwardWins
    )
}
