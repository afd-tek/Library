package com.seda.library.DataAccess.Repository.Abstract;


import androidx.annotation.Nullable;

import com.seda.library.DataAccess.Entites.Book;
import com.seda.library.DataAccess.Repository.IEntityRepo;

import java.util.function.Predicate;

public interface IBookDAL extends IEntityRepo<Book> {

}
