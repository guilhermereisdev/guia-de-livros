package com.guilhermereisapps.guiadelivros.model

data class ReaderUser(
    val id: String? = null,
    val userId: String,
    val displayName: String,
    val avatarUrl: String,
    val quote: String,
    val profession: String
)
