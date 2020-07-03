package com.seda.library.DataAccess.Repository.Concrete.JDBC;

import android.os.Build;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.seda.library.DataAccess.Entites.User;
import com.seda.library.DataAccess.Entites.User;
import com.seda.library.DataAccess.Manager.DatabaseManager;
import com.seda.library.DataAccess.Repository.Abstract.IUserDAL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class UserDAL implements IUserDAL {

    ResultSet resultSet;
    DatabaseManager _manager;

    public UserDAL(DatabaseManager manager) {
        _manager = manager;
    }

    @Override
    public User Add(User entity) {
        String query = "insert into Users(TCKimlik,Name,Surname,UType,Email,Password,StudentNumber,Phone) values('" +
                entity.TCKimlik + "','" + entity.Name + "','" + entity.Surname + "'," + entity.UserType + ",'" + entity.Email + "','" + entity.Password +"','" + entity.Number + "','" + entity.Phone +
                "') select SCOPE_IDENTITY()";

        resultSet = _manager.Query(query);
        try {
            if (resultSet != null && resultSet.next()) {
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
    public User Update(User entity) {
        String query = "update Users set TCKimlik = '" + entity.TCKimlik + "', Name = '"+entity.Name+"',Surname = '"+entity.Surname+
                "', UType = "+entity.UserType+", Email = '"+entity.Email+"', Password = '"+entity.Password+"', StudentNumber = '"+entity.Number+"', Phone = '"+entity.Phone+"' where Id = "+entity.Id;
        _manager.Query(query);
        return Get(entity.Id);
    }

    @Override
    public boolean Delete(int id) {
        String query = "delete from Users where Id = "+id;
        _manager.Statement(query);
        return Get(id) == null;
    }

    @Override
    public User Get(int id) {
        String query = "select * from Users where Id = "+id;
        resultSet = _manager.Query(query);
        try {
            if(resultSet.next()){
                User b = new User(resultSet);
                return b;
            }
            return null;
        } catch (SQLException e) {
            return null;
        }
    }


    @Override
    public List<User> GetList(@Nullable Predicate<User> filter) {
        String query = "select * from Users";
        List<User> Users = new ArrayList<>();
        resultSet = _manager.Query(query);
        while (true){
            try {
                if (!resultSet.next()) {
                    if(filter != null) Users = Users.stream().filter(filter).collect(Collectors.toList());
                    return Users;
                }
                else{
                    Users.add(new User(resultSet));
                }
            } catch (SQLException e) {
                return null;
            }
        }
    }
}
