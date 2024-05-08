package com.guilhermereisapps.guiadelivros.network

import com.guilhermereisapps.guiadelivros.model.book.Book
import com.guilhermereisapps.guiadelivros.model.book.Item
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface BooksApi {
    @GET("volumes")
    suspend fun getAllBooks(@Query("q") query: String): Book

    @GET("volumes/{bookId}")
    suspend fun getBookInfo(@Path("bookId") bookId: String): Item
}
