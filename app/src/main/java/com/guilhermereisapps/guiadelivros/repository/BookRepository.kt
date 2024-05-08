package com.guilhermereisapps.guiadelivros.repository

import com.guilhermereisapps.guiadelivros.data.DataOrException
import com.guilhermereisapps.guiadelivros.model.book.Item
import com.guilhermereisapps.guiadelivros.network.BooksApi
import javax.inject.Inject

class BookRepository @Inject constructor(private val api: BooksApi) {
    suspend fun getBooks(searchQuery: String): DataOrException<List<Item>, Boolean, Exception> {
        val dataOrException = DataOrException<List<Item>, Boolean, Exception>()
        try {
            dataOrException.loading = true
            dataOrException.data = api.getAllBooks(searchQuery).items
            dataOrException.loading = dataOrException.data?.isNotEmpty() != true

        } catch (e: Exception) {
            dataOrException.exception = e
        }
        return dataOrException
    }

    suspend fun getBookInfo(bookId: String): DataOrException<Item, Boolean, Exception> {
        val dataOrException = DataOrException<Item, Boolean, Exception>()
        try {
            dataOrException.loading = true
            dataOrException.data = api.getBookInfo(bookId)
            dataOrException.loading =
                dataOrException.data.toString().isNotEmpty() != true
        } catch (e: Exception) {
            dataOrException.exception = e
        }
        return dataOrException
    }
}
