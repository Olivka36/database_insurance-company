package org.example;

import java.sql.*;
import java.util.Objects;
import java.util.Scanner;

public class Client {
    // Поля класса (атрибуты)
    private String object;
    private String risks;
    private String type;
    private String name;
    private String telephone;

    public boolean exists;

    private static int N = 0;


    static {
        try {
            // Регистрация драйвера
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    // В методах fetchLastID() и savePerson() соединение должно быть закрыто вручную в блоке finally
    public static int fetchLastID() {
        String sql = "SELECT MAX(ID) FROM Client"; // Или другая таблица, где нужно получить ID
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = SQL.connect(); // Получаем соединение
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            if (rs.next()) {
                return rs.getInt(1)+1; // Возвращает максимальное значение ID
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Закрытие ресурсов вручную
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();  // Закрытие соединения вручную
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return 1; // Возвращаем 0, если таблица пуста
    }

    public int getClientId(String name, String telephone) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int clientId = -1; // Если клиент не найден, вернем -1

        try {
            conn = SQL.connect();  // Получаем соединение с базой данных
            String sql = "SELECT ID FROM Client WHERE Full_name = ? AND Telephone = ?";  // Запрос для получения ID клиента
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);  // Устанавливаем имя
            stmt.setString(2, telephone);  // Устанавливаем номер телефона
            rs = stmt.executeQuery();

            if (rs.next()) {
                clientId = rs.getInt("ID");  // Получаем ID из результата
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Закрытие ресурсов вручную
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return clientId;  // Возвращаем ID, если клиент найден, иначе -1
    }


    // Конструктор по умолчанию
    public Client() {
        this.object = null;
        this.risks = null;
        this.type = "Individual";
        this.name = null;
        this.telephone = null;
    }
    // Конструктор с параметрами
    public Client(String object, String risks, String type, String name, String telephone) {
        this.object = object;
        this.risks = risks;
        this.type = type;
        this.name = name;
        this.telephone = telephone;
    }

    public static int getN() {
        return N;
    }

    // Геттеры и сеттеры для полей
    public String getObject() {
        return object;
    }
    public void setObject(String object) {
        this.object = object;
    }

    public String getRisks() {
        return risks;
    }
    public void setRisks(String risks) {
        this.risks = risks;
    }

    public String getType() {
        return type;
    }
    public void setType() {
        Scanner scanner = new Scanner(System.in);
        boolean valid = false;
        System.out.println("Договор будет оформлен на физическое или юридическое лицо?");
        while (!valid) {
            String type = scanner.nextLine().replaceAll("\\s+", "");

            // Проверяем, что введённое значение корректно
            if ("Физическое".equalsIgnoreCase(type) ||
                    "юридическое".equalsIgnoreCase(type)) {
                this.type = type;
                valid = true; // Выход из цикла, если введено корректное значение
            } else {
                System.out.println("Неверное значение. Введите снова");
            }
        }
    }

    public String getName() {
        return name;
    }

    public static String formatString(String input) {
        // Удаляем пробелы в начале и конце строки
        input = input.trim();
        // Разделяем строку на слова
        String[] words = input.split("\\s+");
        StringBuilder formattedString = new StringBuilder();

        for (String word : words) {
            if (!word.isEmpty()) {
                // Приводим первую букву к заглавной, остальные к строчным
                formattedString.append(Character.toUpperCase(word.charAt(0)))
                        .append(word.substring(1).toLowerCase())
                        .append(" ");
            }
        }

        // Удаляем лишний пробел в конце и возвращаем результат
        return formattedString.toString().trim();
    }
    public void setName(String type) {
        Scanner scanner = new Scanner(System.in);
        boolean valid = false;
        if ("Физическое".equalsIgnoreCase(type)) {
            System.out.println("Введите ваше ФИО:");
            while (!valid) {
                String name = scanner.nextLine();
                String[] FIO = name.split("\\s+");
                if (FIO.length < 2) {
                    System.out.println("Неверный формат. Введите снова.");
                } else {
                    name = formatString(name);
                    this.name = name;
                    valid = true;
                }
            }
        } else if ("юридическое".equalsIgnoreCase(type)) {
            System.out.println("Введите название организации:");
            String name = scanner.nextLine();
            this.name = name;
        }
    }

    public String getTelephone() {
        return telephone;
    }
    public void setTelephone() {
        Scanner scanner = new Scanner(System.in);
        boolean valid = false;
        System.out.println("Введите контактный номер телефона:");
        while (!valid) {
            String telephone = scanner.nextLine().replaceAll("\\D", "");

            // Проверяем, что введённое значение корректно
            if (telephone.length() == 10)  {
                this.telephone = telephone;
                valid = true; // Выход из цикла, если введено корректное значение
            } else {
                System.out.println("Неверное значение. Введите номер телефона без 8-, +7-");
            }
        }
    }

    // Метод для вывода информации о человеке
    public void displayInfo() {
        System.out.println();
        System.out.println("Ваши данные: ");
        System.out.println("Объект для страхования: " + object + ".");
        System.out.println("Страхуемые риски: " + risks + ".");
        String type2 = type.substring(0, type.length() - 2) + "ого";
        System.out.println("Страхование для " + type2 + " лица.");
        if ("Физического".equalsIgnoreCase(type2)) {
            System.out.println("ФИО: " + name + ".");
        } else if ("юридического".equalsIgnoreCase(type2)) {
            System.out.println("Наименование организации: " + name + ".");
        }

        if (telephone.length() == 10) {
            // Добавляем "8" в начало, если номер содержит 10 цифр
            String telephone2 = "8" + telephone;

            // Форматируем номер
            telephone2 = telephone2.replaceFirst("(\\d)(\\d{3})(\\d{3})(\\d{2})(\\d{2})", "$1 ($2) $3-$4-$5");
            System.out.println("Контактный телефон: " + telephone2);
        } else {
            System.out.println("Error");
        }
        System.out.println();
    }


    public void saveClient(String name, String telephone) {
        String sql = "INSERT INTO Client (ID, Full_name, Type_of_person, Telephone) VALUES (?, ?, ?, ?)";

        if (!exists){
            N = fetchLastID(); // Инициализация через метод
        } else {
            N = getClientId(name, telephone);
        }

        try (Connection conn = SQL.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, N);
            stmt.setString(2, name);
            stmt.setString(3, type);
            stmt.setString(4, telephone);

            stmt.executeUpdate();
        } catch (SQLException e) {
//            e.printStackTrace();
        }
    }

    public boolean isExists(String name, String telephone) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        exists = false;

        try {
            conn = SQL.connect();  // Получаем соединение с базой данных
            String sql = "SELECT COUNT(*) FROM Client WHERE UPPER(Full_name) = UPPER(?) AND Telephone = ?";  // Запрос для проверки существования по имени и телефону
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);  // Устанавливаем имя
            stmt.setString(2, telephone);  // Устанавливаем номер телефона
            rs = stmt.executeQuery();

            if (rs.next()) {
                int count = rs.getInt(1);
                exists = (count > 0);  // Если count > 0, то запись найдена
            }
        } catch (SQLException e) {
//            e.printStackTrace();
        } finally {
            // Закрытие ресурсов вручную
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
//                e.printStackTrace();
            }
        }

        return exists;
    }

}