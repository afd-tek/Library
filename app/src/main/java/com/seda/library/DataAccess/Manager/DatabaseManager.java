package com.seda.library.DataAccess.Manager;

import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {

    public Connection conn;
    private String errorCode = "";
    private boolean haveanError;

    public Connection getConnection(){

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        String ConnURL = null;
        Connection connection = null;
        String ip = "192.168.1.33";
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
            ConnURL = "jdbc:jtds:sqlserver://"+ip+":1433;"
                    + "instanceName=Ahmet\\SQLEXPRESS;"
                    + "databaseName=library;"
                    + "integratedSecurity=true;"
                    + "user=sa;"
                    + "password=1908;";

            connection = DriverManager.getConnection(ConnURL);
        } catch (InstantiationException e) {
            errorCode += "0xCR0\n";
            Log.e("Error ", e.getMessage());
            haveanError = true;
            return null;
        } catch (IllegalAccessException e) {
            errorCode += "0xCR1\n";
            Log.e("Error ", e.getMessage());
            haveanError = true;
            return null;
        } catch (ClassNotFoundException e) {
            errorCode += "0xCR2\n";
            Log.e("Error ", e.getMessage());
            haveanError = true;
            return null;
        } catch (SQLException e) {
            errorCode += "0xCR3\n";
            Log.e("Error ", e.getMessage());
            haveanError = true;
            return null;
        } catch(Exception e){
            errorCode += "0xCR4\n";
            Log.e("Error ", e.getMessage());
            haveanError = true;
            return null;
        }


        return connection;
    }


    public ResultSet Query(String query){
        ResultSet resultSet = null;
        conn = getConnection();
        if(!haveanError) {
            try {
                Statement statement = conn.createStatement();
                resultSet = statement.executeQuery(query);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else{
            haveanError = false;
        }
        return resultSet;
    }

    public void Statement(String query){
        conn = getConnection();
        if(!haveanError) {
            try {
                Statement statement = conn.createStatement();
                statement.execute(query);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else{
            haveanError = false;
        }
    }
}
