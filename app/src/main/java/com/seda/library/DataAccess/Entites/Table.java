package com.seda.library.DataAccess.Entites;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Table {
    public int Id,Owner,RoomId;
    public String TableName;
    public boolean State;

    public Table(){}

    public Table(ResultSet resultSet){
        try {
            this.Id = resultSet.getInt(1);
            this.RoomId = resultSet.getInt(2);
            this.TableName = resultSet.getString(3);
            this.State = resultSet.getBoolean(4);
            if(this.State) this.Owner = resultSet.getInt(5);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
