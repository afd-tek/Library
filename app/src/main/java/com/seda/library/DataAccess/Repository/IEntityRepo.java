package com.seda.library.DataAccess.Repository;

import androidx.annotation.Nullable;

import com.seda.library.DataAccess.Manager.DatabaseManager;

import java.util.List;
import java.util.function.Predicate;

public interface IEntityRepo<T> {

    T Add(T entity);

    T Update(T entity);

    boolean Delete(int id);

    T Get(int id);

    List<T> GetList(@Nullable Predicate<T> filter);
}
