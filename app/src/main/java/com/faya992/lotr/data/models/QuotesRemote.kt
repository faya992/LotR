package com.faya992.lotr.data.models

import com.google.gson.annotations.SerializedName

data class QuotesRemote(
    @SerializedName("docs") val docs: List<QuoteRemote>,
    @SerializedName("limit") val limit: Int,
    @SerializedName("offset") val offset: Int,
    @SerializedName("total") val total: Int,
    @SerializedName("pages") val pages: Int,
    @SerializedName("page") val page: Int
)

data class QuoteRemote(
    @SerializedName("_id") val _id : String,
    @SerializedName("dialog") val dialog : String,
    @SerializedName("movie") val movie : String,
    @SerializedName("character") val character : String,
    @SerializedName("id") val id : String
)