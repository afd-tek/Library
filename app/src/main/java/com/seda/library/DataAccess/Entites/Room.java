package com.seda.library.DataAccess.Entites;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Room {
    public int Id;
    public String Name;

    public Room(){}

    public Room(ResultSet resultSet){
        try {
            this.Id = resultSet.getInt(1);
            this.Name = resultSet.getString(2);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
