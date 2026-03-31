package org.example.cybergaming.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection{
    private static Connection connection;;
    private static final String URL = "jdbc:mysql://localhost:3306/cyber_gaming";
    private static final String USER = "root";
    private static final String PASSWORD = "1234";
    private DBConnection(){}
    public static Connection getConnection(){
        try {
            if(connection==null||connection.isClosed()){
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return connection;
    }
}

