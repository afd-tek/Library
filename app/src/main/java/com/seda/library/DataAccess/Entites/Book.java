package com.seda.library.DataAccess.Entites;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Book {
    public int Id,Taker;
    public boolean IsTaken;
    public Date State;
    public String Name,Author,Desk;

    public Book(){}

    public Book(ResultSet resultSet){
        try {
            this.Id = resultSet.getInt(1);
            this.Name = resultSet.getString(2);
            this.Author = resultSet.getString(3);
            this.State = resultSet.getDate(4);
            this.IsTaken = resultSet.getBoolean(6);
            this.Desk = resultSet.getString(7);
            if(this.IsTaken) this.Taker = resultSet.getInt(5);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
