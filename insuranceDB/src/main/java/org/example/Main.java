package org.example;

import org.example.Application;
import org.example.Client;
import org.example.Contract;
import org.example.Payment;
import org.example.SQL;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); // Создаем объект Scanner

        while (true){
            Connection conn = SQL.connect();
            System.out.println();
            System.out.println("Нажмите 1 для подачи заявки");
            System.out.println("Нажмите 2 для просмотра базы данных клиентов");
            System.out.println("Нажмите 3 для просмотра базы данных договоров");
            System.out.println("Нажмите 4 для просмотра базы данных филиалов");
            System.out.println("Нажмите 5 для \"открытия\" нового филиала");
            System.out.println("Нажмите 0 для выхода");

            int o = scanner.nextInt();
            scanner.nextLine();
            if (o == 0){
                System.out.println("Всего хорошего!");
                break;
            }

            if (o == 2){
                DisplayDatabase.displayClients(conn);
            }

            else if (o == 3){
                DisplayDatabase.displayContracts(conn);
            }

            else if (o == 4){
                DisplayDatabase.displayBranches(conn);
            }

            else if (o == 1){
                Client client1 = new Client();
                Contract contract = new Contract();
                Payment pay = new Payment();
                while (contract.getObject() == null) {
                    System.out.println("Какой объект вы хотите застраховать? Введите в именительном падеже.");
                    String object = scanner.nextLine(); // Считываем строку
                    client1.setObject(object);

                    contract.setObject(object);
                }

                while (contract.getType_insurance() == null) {
                    System.out.println("От каких рисков? Введите конкретный риск");
                    String risks = scanner.nextLine(); // Считываем строку
                    client1.setRisks(risks);

                    contract.setType_insurance(risks);
                }

                client1.setType();

                String type = client1.getType();
                client1.setName(type);

                client1.setTelephone();

                client1.displayInfo();
                System.out.println("Ваши данные верны?");
                String answer = scanner.nextLine(); // Считываем строку
                answer = answer.replaceAll("\\s+", "");

                while (!"Да".equalsIgnoreCase(answer)) {
                    if (answer.equalsIgnoreCase("нет")){
                        System.out.println("Введите что хотите изменить:");
                        answer = scanner.nextLine();
                        client1 = handleCommand(answer, client1);
                        contract.setObject(client1.getObject());
                        contract.setType_insurance(client1.getRisks());
                    }
                    client1.displayInfo();
                    System.out.println("Ваши данные верны?");
                    answer = scanner.nextLine(); // Считываем строку
                    answer = answer.replaceAll("\\s+", "");
                }
                client1.isExists(client1.getName(), client1.getTelephone());
                client1.saveClient(client1.getName(), client1.getTelephone());

                Application app1 = new Application(client1.getObject(), client1.getRisks(), client1.getType(), client1.getName(), client1.getTelephone());
                app1.submitApplication();
                System.out.println(app1);
                System.out.println();

                contract.setClient_type(app1.getType());
                contract.setName(app1.getName());
                contract.setTelephone(app1.getTelephone());

                contract.setSum();

                System.out.println("Обработка заявки...");
                processApplication(3000);

                contract.setPrice();

                contract.displayInfo();

                System.out.println("Вас устраивают условия договора?");
                String answer2 = scanner.nextLine(); // Считываем строку
                answer2 = answer2.replaceAll("\\s+", "");

                int k = 0;

                while (!"Да".equalsIgnoreCase(answer2)) {
                    if (contract.getK() > 3){
                        System.out.println("Всего доброго!");
                        System.out.println();
                        k = 10;
                        break;
                    } else if (contract.getK() == 3) {
                        contract.setBet();
                    } else {
                        if (answer2.equalsIgnoreCase("нет")){
                            System.out.println("Что хотите изменить?");
                            answer2 = scanner.nextLine();
                            contract = handleCommand(answer2, contract);
                        } else {
                            System.out.println("Введите да/нет:");
                            answer2 = scanner.nextLine();
                        }
                    }
                    contract.displayInfo();

                    System.out.println("Вас устраивают условия договора?");
                    answer2 = scanner.nextLine(); // Считываем строку
                    answer2 = answer2.replaceAll("\\s+", "");
                }

                if (k != 10){
                    System.out.println("Отлично! Сумма к оплате: " + String.format("%.3f", contract.getPrice()) + ". Вот квитанция на оплату.");
                    pay.setPrice(contract.getPrice());
                    processApplication(1000);
                    pay.paymentProcessing();
                    processApplication(1000);
                    contract.concludedAgreement(pay.payment);
                    contract.saveContract(client1);
                }


            } else if (o == 5) {
                System.out.print("Введите название филиала: ");
                String name = scanner.nextLine();
                System.out.print("Введите адрес филиала: ");
                String address = scanner.nextLine();
                System.out.print("Введите контактный телефон филиала: ");
                String telephone = scanner.nextLine();
                int id = LastID() + 1;
                String sql = "INSERT INTO Branch (ID, Name, Address, Telephone) VALUES (?, ?, ?, ?)";
                try (conn;
                     PreparedStatement stmt = conn.prepareStatement(sql)) {

                    stmt.setInt(1, id);
                    stmt.setString(2, name);
                    stmt.setString(3, address);
                    stmt.setString(4, telephone);

                    stmt.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Ошибка. Введите другое значение");
            }
        }
    }

    public static void processApplication(int n) {
        try {
            Thread.sleep(n);  // Симуляция задержки в 5 секунд
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static Client handleCommand(String command, Client client) {
        command = command.replaceAll("\\s+", "");
        Scanner scanner = new Scanner(System.in); // Создаем объект Scanner
        command = command.toLowerCase(); // Приводим к нижнему регистру для удобства обработки

        if (command.contains("фио") || command.contains("имя") || command.contains("наименования") || command.contains("название") || command.contains("наименование")) {
            client.setName(client.getType());
        } else if (command.contains("номер телефона") || command.contains("номер") || command.contains("телефон")) {
            client.setTelephone();
        } else if (command.contains("объект")) {
            System.out.println("Какой объект вы хотите засраховать?");
            String object = scanner.nextLine(); // Считываем строку
            client.setObject(object);
            System.out.println("Какие страховые риски?");
            String risks = scanner.nextLine(); // Считываем строку
            client.setRisks(risks);
        } else if (command.contains("риски") || command.contains("риск")) {
            System.out.println("Какие страховые риски?");
            String risks = scanner.nextLine(); // Считываем строку
            client.setRisks(risks);
        } else {
            System.out.println("Введите что хотите изменить:");
        }
        return client;
    }

    public static Contract handleCommand(String command, Contract contract) {
        command = command.replaceAll("\\s+", "");
        Scanner scanner = new Scanner(System.in); // Создаем объект Scanner
        command = command.toLowerCase(); // Приводим к нижнему регистру для удобства обработки

        if (command.contains("ставку") || command.contains("тарифную") || command.contains("тариф") || command.contains("ставка")) {
            contract.setBet();
            contract.setPrice();
        } else if (command.contains("сумму") || command.contains("страхования") || command.contains("сумма") || command.contains("страхование")) {
            contract.changeSum();
            contract.setPrice();
        } else if (command.contains("стоимость") || command.contains("договора") || command.contains("итоговую") || command.contains("цену") || command.contains("цена") || command.contains("оплата")) {
            contract.setBet();
            contract.setPrice();
        } else {
            System.out.println("Введите что хотите изменить:");
        }
        return contract;
    }

    public static int LastID() {
        String sql = "SELECT MAX(ID) FROM Branch"; // Или другая таблица, где нужно получить ID
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = SQL.connect(); // Получаем соединение
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            if (rs.next()) {
                return rs.getInt(1); // Возвращает максимальное значение ID
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
        return 0; // Возвращаем 0, если таблица пуста
    }

}