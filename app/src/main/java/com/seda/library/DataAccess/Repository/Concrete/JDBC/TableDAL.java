package com.seda.library.DataAccess.Repository.Concrete.JDBC;

import android.os.Build;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.seda.library.DataAccess.Entites.Table;
import com.seda.library.DataAccess.Entites.Table;
import com.seda.library.DataAccess.Manager.DatabaseManager;
import com.seda.library.DataAccess.Repository.Abstract.ITableDAL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class TableDAL implements ITableDAL {
    ResultSet resultSet;
    DatabaseManager _manager;

    public TableDAL(DatabaseManager manager) {
        _manager = manager;
    }

    @Override
    public Table Add(Table entity) {
        String query = "insert into Tables(Room,Name,State,Owner) values(" +
                entity.RoomId + ",'"  + entity.TableName +"'," + (entity.State ? 1 : 0) + "," + entity.Owner + ") select SCOPE_IDENTITY()";

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
    public Table Update(Table entity) {
        String query = "update Tables set Room = " + entity.RoomId + ", State = "+(entity.State ? 1 : 0)+",Name = '"+entity.TableName+
                "', Owner = '"+entity.Owner+" where Id = "+entity.Id;
        _manager.Query(query);
        return Get(entity.Id);
    }

    @Override
    public boolean Delete(int id) {
        String query = "delete from Tables where Id = "+id;
        _manager.Statement(query);
        return Get(id) == null;
    }

    @Override
    public Table Get(int id) {
        String query = "select * from Tables where Id = "+id;
        resultSet = _manager.Query(query);
        try {
            if(resultSet.next()){
                Table b = new Table(resultSet);
                return b;
            }
            return null;
        } catch (SQLException e) {
            return null;
        }
    }


    @Override
    public List<Table> GetList(@Nullable Predicate<Table> filter) {
        String query = "select * from Tables";
        List<Table> Tables = new ArrayList<>();
        resultSet = _manager.Query(query);
        while (true){
            try {
                if (!resultSet.next()) {
                    return filter == null ? Tables : Tables.stream().filter(filter).collect(Collectors.toList());
                }
                else{
                    Tables.add(new Table(resultSet));
                }
            } catch (SQLException e) {
                return null;
            }
        }
    }
}
