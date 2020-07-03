package com.seda.library.DataAccess.Retrofit.Services;

import com.seda.library.DataAccess.Entites.Book;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface IBookService {
    @GET("Books/{id}")
    Call<Book> Get(@Path("id") int id);

    @GET("Books")
    Call<List<Book>> GetAll();

    @POST("Books/filter")
    Call<List<Book>> GetFilter(@Body Book entity);

    @POST("Books")
    Call<Book> Add(@Body Book entity);

    @PUT("Books")
    Call<Book> Update(@Body Book entity);

    @DELETE("Books/{id}")
    Call<String> Delete(@Path("id") int id);
}
