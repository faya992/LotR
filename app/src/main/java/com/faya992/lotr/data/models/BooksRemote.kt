package com.faya992.lotr.data.models

data class BooksRemote (
    val docs: List<BookRemote>,
    val total: Int, val limit: Int, val offset: Int,
    val page: Int, val pages: Int
)

data class BookRemote(
    val _id: String,
    val chapterName: String
)