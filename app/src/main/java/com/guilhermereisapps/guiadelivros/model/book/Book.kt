package com.guilhermereisapps.guiadelivros.model.book

data class Book(
    val items: List<Item>,
    val kind: String,
    val totalItems: Int
)