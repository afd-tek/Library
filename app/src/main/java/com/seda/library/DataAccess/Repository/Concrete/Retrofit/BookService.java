package com.seda.library.DataAccess.Repository.Concrete.Retrofit;

import androidx.annotation.Nullable;

import com.seda.library.DataAccess.Entites.Book;
import com.seda.library.DataAccess.Repository.IEntityRepo;
import com.seda.library.DataAccess.Repository.IEntityService;
import com.seda.library.DataAccess.Retrofit.ApiService;
import com.seda.library.DataAccess.Retrofit.Services.IBookService;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.function.Predicate;

import retrofit2.Response;

public class BookService implements IEntityService<Book> {
    IBookService service;
    Book reEntity;

    public BookService() {
        service = ApiService.getClient().create(IBookService.class);
    }

    @Override
    public Future<Book> Add(Book entity) {
        return null;
    }

    @Override
    public Future<Book> Update(Book entity) {
        return null;
    }

    @Override
    public boolean Delete(int id) {
        return false;
    }

    @Override
    public CompletableFuture<Book> Get(int id) {
        Book book = new Book();
        return CompletableFuture.supplyAsync(() -> {
            try {
                Response<Book> b = service.Get(id).execute();
                if (b != null && b.isSuccessful()) {
                    return b.body();
                } else {
                    return null;
                }
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        });

    }

    @Override
    public Future<List<Book>> GetList(@Nullable Predicate<Book> filter) {
        return null;
    }
}
