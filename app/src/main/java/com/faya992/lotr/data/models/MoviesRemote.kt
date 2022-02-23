package com.faya992.lotr.data.models

data class MoviesRemote (
    val docs: List<MovieRemote>,
    val total: Int, val limit: Int, val offset: Int,
    val page: Int, val pages: Int
)

data class MovieRemote(
    val _id: String, val name: String, val runtimeInMinutes: Int,
    val budgetInMillions: Int, val boxOfficeRevenueInMillions: Double,
    val academyAwardNominations: Int, val academyAwardWins: Int,
    val rottenTomatoesScore: Int
)