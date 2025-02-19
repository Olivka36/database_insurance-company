package org.example;

import java.sql.*;

public class DisplayDatabase {
    public static void displayClients(Connection conn) {
        try {
            String sql = "SELECT * FROM Client";  // Запрос для получения всех данных из таблицы клиентов
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            System.out.println("\nТаблица: Clients");
            System.out.println("--------------------------------------------------------------------------------------------------------------");

            // Определение количества колонок
            int columnCount = rs.getMetaData().getColumnCount();

            // Вывод заголовков колонок
            System.out.print(String.format("%-10s", rs.getMetaData().getColumnName(1)));
            System.out.print(String.format("%-50s", rs.getMetaData().getColumnName(2)));
            System.out.print(String.format("%-30s", rs.getMetaData().getColumnName(3)));
            System.out.print(String.format("%-35s", rs.getMetaData().getColumnName(4)));

            System.out.println();
            System.out.println("--------------------------------------------------------------------------------------------------------------");

            // Вывод строк данных из таблицы клиентов
            while (rs.next()) {
                System.out.print(String.format("%-10s", rs.getString(1)));
                System.out.print(String.format("%-50s", rs.getString(2)));
                System.out.print(String.format("%-30s", rs.getString(3)));
                System.out.print(String.format("%-35s", rs.getString(4)));
                System.out.println();
            }
            rs.close();
            stmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Функция для отображения таблицы договоров
    public static void displayContracts(Connection conn) {
        try {
            String sql = "SELECT * FROM Contract";  // Запрос для получения всех данных из таблицы договоров
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            System.out.println("\nТаблица: Contracts");
            System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------");

            // Определение количества колонок
            int columnCount = rs.getMetaData().getColumnCount();

            // Вывод заголовков колонок
            System.out.print(String.format("%-10s", rs.getMetaData().getColumnName(1)));
            System.out.print(String.format("%-15s", rs.getMetaData().getColumnName(2)));
            System.out.print(String.format("%-50s", rs.getMetaData().getColumnName(3)));
            for (int i = 4; i <= columnCount; i++) {
                System.out.print(String.format("%-20s", rs.getMetaData().getColumnName(i)));
            }
            System.out.println();
            System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------");

            // Вывод строк данных из таблицы договоров
            while (rs.next()) {
                System.out.print(String.format("%-10s", rs.getString(1)));
                System.out.print(String.format("%-15s", rs.getString(2)));
                System.out.print(String.format("%-50s", rs.getString(3)));
                for (int i = 4; i <= columnCount; i++) {
                    System.out.print(String.format("%-20s", rs.getString(i)));
                }
                System.out.println();
            }
            rs.close();
            stmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void displayBranches(Connection conn) {
        try {
            String sql = "SELECT * FROM Branch";  // Запрос для получения всех данных из таблицы договоров
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            System.out.println("\nТаблица: Branches");
            System.out.println("---------------------------------------------------------------------------------------------");

            // Определение количества колонок
            int columnCount = rs.getMetaData().getColumnCount();

            // Вывод заголовков колонок
            System.out.print(String.format("%-10s", rs.getMetaData().getColumnName(1)));
            for (int i = 2; i <= columnCount; i++) {
                System.out.print(String.format("%-30s", rs.getMetaData().getColumnName(i)));
            }
            System.out.println();
            System.out.println("---------------------------------------------------------------------------------------------");

            // Вывод строк данных из таблицы договоров
            while (rs.next()) {
                System.out.print(String.format("%-10s", rs.getString(1)));
                for (int i = 2; i <= columnCount; i++) {
                    System.out.print(String.format("%-30s", rs.getString(i)));
                }
                System.out.println();
            }
            rs.close();
            stmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}