package com.seda.library.DataAccess.Repository;

import androidx.annotation.Nullable;

import com.seda.library.DataAccess.Manager.DatabaseManager;

import java.util.List;
import java.util.concurrent.Future;
import java.util.function.Predicate;

public interface IEntityService<T> {

    Future<T> Add(T entity);

    Future<T> Update(T entity);

    boolean Delete(int id);

    Future<T> Get(int id);

    Future<List<T>> GetList(@Nullable Predicate<T> filter);
}
