package com.seda.library.DataAccess.Repository.Concrete.JDBC;

import android.os.Build;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.seda.library.DataAccess.Entites.Book;
import com.seda.library.DataAccess.Manager.DatabaseManager;
import com.seda.library.DataAccess.Repository.Abstract.IBookDAL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class BookDAL implements IBookDAL {

    ResultSet resultSet;
    DatabaseManager _manager;

    public BookDAL(DatabaseManager manager) {
        _manager = manager;
    }

    @Override
    public Book Add(Book entity) {
        String query = "insert into Books(Taker,IsTaken,State,Name,Author,Desk) values(" +
                 entity.Taker + "," + (entity.IsTaken ? 1 : 0) + ",'" + entity.State + "','" + entity.Name + "','" + entity.Author + "','" + entity.Desk + "') select SCOPE_IDENTITY()";

        resultSet = _manager.Query(query);
        try {
            if (resultSet.next()) {
                int id = 0;
                id = resultSet.getInt(1);
                if (id > 0){
                    return Get(id);
                }
            }
            return null;
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public Book Update(Book entity) {
        String query = "update Books set Taker = " + entity.Taker + ", IsTaken = "+(entity.IsTaken? 1 : 0)+",State = '"+entity.State+
                "', Name = '"+entity.Name+"', Author = '"+entity.Author+"', Desk = '"+entity.Desk+"' where Id = "+entity.Id;
        _manager.Query(query);
        return Get(entity.Id);
    }

    @Override
    public boolean Delete(int id) {
        String query = "delete from Books where Id = "+id;
        _manager.Statement(query);
        return Get(id) == null;
    }

    @Override
    public Book Get(int id) {
        String query = "select * from Books where Id = "+id;
        resultSet = _manager.Query(query);
        try {
            if(resultSet.next()){
                Book b = new Book(resultSet);
                return b;
            }
            return null;
        } catch (SQLException e) {
            return null;
        }
    }


    @Override
    public List<Book> GetList(@Nullable Predicate<Book> filter) {
        String query = "select * from Books";
        List<Book> books = new ArrayList<>();
        resultSet = _manager.Query(query);
        while (true){
            try {
                if (!resultSet.next()) {
                    return filter == null ? books : books.stream().filter(filter).collect(Collectors.toList());
                }
                else{
                    books.add(new Book(resultSet));
                }
            } catch (SQLException e) {
                return null;
            }
        }
    }

}
