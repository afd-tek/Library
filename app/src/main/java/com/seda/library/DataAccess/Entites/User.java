package com.seda.library.DataAccess.Entites;

import java.sql.ResultSet;
import java.sql.SQLException;

public class User {

    public int Id,UserType;
    public String TCKimlik,Name,Surname,Email,Password,Number,Phone;

    public User(){}

    public User(ResultSet resultSet){
        try {
            this.Id = resultSet.getInt(1);
            this.TCKimlik = resultSet.getString(2);
            this.Name = resultSet.getString(3);
            this.Surname = resultSet.getString(4);
            this.UserType = resultSet.getInt(5);
            this.Email = resultSet.getString(6);
            this.Password = resultSet.getString(7);
            this.Number = resultSet.getString(8);
            this.Phone = resultSet.getString(9);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
