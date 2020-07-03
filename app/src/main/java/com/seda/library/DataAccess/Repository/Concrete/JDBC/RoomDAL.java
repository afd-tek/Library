package com.seda.library.DataAccess.Repository.Concrete.JDBC;

import android.os.Build;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.seda.library.DataAccess.Entites.Room;
import com.seda.library.DataAccess.Entites.Room;
import com.seda.library.DataAccess.Manager.DatabaseManager;
import com.seda.library.DataAccess.Repository.Abstract.IRoomDAL;
import com.seda.library.DataAccess.Repository.IEntityRepo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class RoomDAL implements IRoomDAL {
    ResultSet resultSet;
    DatabaseManager _manager;

    public RoomDAL(DatabaseManager manager) {
        _manager = manager;
    }

    @Override
    public Room Add(Room entity) {
        String query = "insert into Rooms(Name) values('" +entity.Name + "') select SCOPE_IDENTITY()";

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
    public Room Update(Room entity) {
        String query = "update Rooms set Name = '"+entity.Name+"' where Id = "+entity.Id;
        _manager.Query(query);
        return Get(entity.Id);
    }

    @Override
    public boolean Delete(int id) {
        String query = "delete from Rooms where Id = "+id;
        _manager.Statement(query);
        return Get(id) == null;
    }

    @Override
    public Room Get(int id) {
        String query = "select * from Rooms where Id = "+id;
        resultSet = _manager.Query(query);
        try {
            if(resultSet.next()){
                Room b = new Room(resultSet);
                return b;
            }
            return null;
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public List<Room> GetList(@Nullable Predicate<Room> filter) {
        String query = "select * from Rooms";
        List<Room> rooms = new ArrayList<>();
        resultSet = _manager.Query(query);
        while (true){
            try {
                if (!resultSet.next()) {
                    return filter == null ? rooms : rooms.stream().filter(filter).collect(Collectors.toList());
                }
                else{
                    rooms.add(new Room(resultSet));
                }
            } catch (SQLException e) {
                return null;
            }
        }
    }
}
