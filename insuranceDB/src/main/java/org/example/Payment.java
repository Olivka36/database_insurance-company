package org.example;

import java.util.Scanner;

public class Payment {
    private double price;
    public boolean payment;

    public Payment() {
        this.price = price;
        this.payment = false;
    }

    public Payment(double price) {
        this.price = price;
        this.payment = false;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void paymentProcessing(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Оплата будет картой/наличными?");
        String str = scanner.nextLine();
        int rand = (int) (Math.random() * (2));
        System.out.println("Обработка платежа...");
        processApplication(2000);
        while (rand == 0){
            System.out.println("Ошибка платежа. Повторить снова?");
            String str2 = scanner.nextLine();
            if (str2.equalsIgnoreCase("да")){
                rand = (int) (Math.random() * (2));
            } else if (str2.equalsIgnoreCase("нет")){
                System.out.println("Договор не оплачен.");
                break;
            } else {
                System.out.println("Введите да/нет:");
            }
            System.out.println("Обработка платежа...");
            processApplication(2000);
        }
        if (rand == 1){
            System.out.println("Платеж успешно прошел! Спасибо что обратились к нам!");
            payment = true;
        }


    }

    public static void processApplication(int n) {
        try {
            Thread.sleep(n);  // Симуляция задержки в 2 секунд
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
