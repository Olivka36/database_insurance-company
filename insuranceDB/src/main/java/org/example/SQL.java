package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQL {

    private static final String URL = "jdbc:sqlite:insurance.db";

    public static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL);  // Соединение остается открытым
//            if (conn != null) {
//                System.out.println("Соединение с базой данных установлено.");
//            }
        } catch (SQLException e) {
            System.err.println("Ошибка подключения к базе данных: " + e.getMessage());
        }
        return conn;  // Возвращаем соединение, которое не будет закрыто автоматически
    }
}
