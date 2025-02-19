package org.example;

import java.sql.*;
import java.sql.Date;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

public class Contract {

    private static final Map<String, double[]> dic;

    static {
        Map<String, double[]> tempMap = new HashMap<>();
        tempMap.put("КАСКО (страхование от угона)", new double[]{0.01, 0.02});
        tempMap.put("КАСКО (страхование от повреждений)", new double[]{0.03, 0.05});
        tempMap.put("ОСАГО (страхование ответственности при ДТП)", new double[]{0.005, 0.01});
        tempMap.put("Полное КАСКО", new double[]{0.05, 0.1});

        tempMap.put("Медицинское страхование", new double[]{0.05, 0.15});
        tempMap.put("Страхование жизни", new double[]{0.001, 0.01});
        tempMap.put("Страхование от несчастных случаев", new double[]{0.002, 0.02});

        tempMap.put("Страхование недвижимости от пожара", new double[]{0.0005, 0.003});
        tempMap.put("Страхование недвижимости от затопления", new double[]{0.001, 0.005});
        tempMap.put("Страхование имущества от кражи", new double[]{0.001, 0.004});

        tempMap.put("Страхование груза от повреждений", new double[]{0.001, 0.005});
        tempMap.put("Страхование груза от потери", new double[]{0.002, 0.01});

        tempMap.put("Страхование путешествий (медицинские расходы)", new double[]{0.01, 0.03});
        tempMap.put("Страхование отмены поездки", new double[]{0.05, 0.05});

        tempMap.put("Страхование здоровья животных", new double[]{0.01, 0.05});
        tempMap.put("Страхование животных от утраты", new double[]{0.01, 0.03});

        dic = Collections.unmodifiableMap(tempMap);
    }

    private String client_type;
    private String name;
    private String telephone;
    private String type_insurance;
    private String object;
    private double sum;
    private double bet;
    private double price;
    private Date date;
    private final String[] state;
    private double first_sum;

    private static int N;

    static {
        N = fetchLast(); // Инициализация через метод
    }

    public static int fetchLast() {
        String sql = "SELECT MAX(ID) FROM Contract"; // Или другая таблица, где нужно получить ID
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

    public int k = 0;

    public Contract() {
        this.client_type = "Individual";
        this.name = null;
        this.telephone = null;
        this.type_insurance = null;
        this.sum = 0;
        this.bet = 0;
        this.price = 0;
        this.date = Date.valueOf(LocalDate.now());
        this.state = new String[]{"идеальное", "хорошее", "нормальное", "плохое", "ужасное"};
        this.first_sum = 0;
        N++;
        this.k = 0;
    }

    public Contract(String client_type, String name, String telephone) {
        this.client_type = client_type;
        this.name = name;
        this.telephone = telephone;
        this.date = Date.valueOf(LocalDate.now());
        this.state = new String[]{"идеальное", "хорошее", "нормальное", "плохое", "ужасное"};
        N++;
        this.k = 0;
    }

    public String getClient_type() {
        return client_type;
    }
    public void setClient_type(String client_type) {
        this.client_type = client_type;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getTelephone() {
        return telephone;
    }
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getType_insurance() {
        return type_insurance;
    }
    public void setType_insurance(String risks) {
        if (object == null || risks == null) {
            //System.out.println("Ошибка. Некорректные данные.");
        }
        else{
            String[] risk = risks.split("\\s+");
            object = object.replaceAll("\\s+", "");
            int n = risk.length - 1;
            switch (object.toLowerCase()) {
                case "автомобиль", "машина":
                    if (risk[n].equalsIgnoreCase("угон") || risk[n].equalsIgnoreCase("угона")) {
                        this.type_insurance = "КАСКО (страхование от угона)";
                        double[] rates = dic.get(this.type_insurance);
                        double b = Math.random() * (rates[1] - rates[0]) + rates[0];
                        this.bet = Math.round(b * 1000.0) / 1000.0;
                    } else if (risk[n].equalsIgnoreCase("повреждение") || risk[n].equalsIgnoreCase("повреждения") || risk[n].equalsIgnoreCase("повреждений")) {
                        this.type_insurance = "КАСКО (страхование от повреждений)";
                        double[] rates = dic.get(this.type_insurance);
                        double b = Math.random() * (rates[1] - rates[0]) + rates[0];
                        this.bet = Math.round(b * 1000.0) / 1000.0;
                    } else if (risk[n].equalsIgnoreCase("дтп") || risk[n].equalsIgnoreCase("авария") || risk[n].equalsIgnoreCase("аварий")) {
                        this.type_insurance = "ОСАГО (страхование ответственности при ДТП)";
                        double[] rates = dic.get(this.type_insurance);
                        double b = Math.random() * (rates[1] - rates[0]) + rates[0];
                        this.bet = Math.round(b * 1000.0) / 1000.0;
                    } else if (risk[n].equalsIgnoreCase("всего") || risk[n].equalsIgnoreCase("всё") || risk[n].equalsIgnoreCase("все") || risk[n].equalsIgnoreCase("вместе")) {
                        this.type_insurance = "Полное КАСКО";
                        double[] rates = dic.get(this.type_insurance);
                        double b = Math.random() * (rates[1] - rates[0]) + rates[0];
                        this.bet = Math.round(b * 1000.0) / 1000.0;
                    }
                    break;

                case "жизнь", "здоровье":
                    if (risk[n].equalsIgnoreCase("болезнь") || risk[n].equalsIgnoreCase("болезни") || risk[n].equalsIgnoreCase("болезней")
                            || risk[n].equalsIgnoreCase("заболеваний")) {
                        this.type_insurance = "Медицинское страхование";
                        double[] rates = dic.get(this.type_insurance);
                        double b = Math.random() * (rates[1] - rates[0]) + rates[0];
                        this.bet = Math.round(b * 1000.0) / 1000.0;
                    } else if (risk[n].equalsIgnoreCase("смерть") || risk[n].equalsIgnoreCase("смерти")) {
                        this.type_insurance = "Страхование жизни";
                        double[] rates = dic.get(this.type_insurance);
                        double b = Math.random() * (rates[1] - rates[0]) + rates[0];
                        this.bet = Math.round(b * 1000.0) / 1000.0;
                    } else if (risk[n].equalsIgnoreCase("травма") || risk[n].equalsIgnoreCase("травмы") || risk[n].equalsIgnoreCase("травм") || risk[n].equalsIgnoreCase("повреждений")
                            || risk[n].equalsIgnoreCase("вреда") || risk[n].equalsIgnoreCase("вред")) {
                        this.type_insurance = "Страхование от несчастных случаев";
                        double[] rates = dic.get(this.type_insurance);
                        double b = Math.random() * (rates[1] - rates[0]) + rates[0];
                        this.bet = Math.round(b * 1000.0) / 1000.0;
                    }
                    break;

                case "недвижимость", "дом", "квартира":
                    if (risk[n].equalsIgnoreCase("пожар") || risk[n].equalsIgnoreCase("пожара") || risk[n].equalsIgnoreCase("огня")
                            || risk[n].equalsIgnoreCase("огонь")) {
                        this.type_insurance =  "Страхование недвижимости от пожара";
                        double[] rates = dic.get(this.type_insurance);
                        double b = Math.random() * (rates[1] - rates[0]) + rates[0];
                        this.bet = Math.round(b * 1000.0) / 1000.0;
                    } else if (risk[n].equalsIgnoreCase("затопление") || risk[n].equalsIgnoreCase("потоп") || risk[n].equalsIgnoreCase("затопления")
                            || risk[n].equalsIgnoreCase("потопа") || risk[n].equalsIgnoreCase("наводнения") || risk[n].equalsIgnoreCase("наводнение")) {
                        this.type_insurance =  "Страхование недвижимости от затопления";
                        double[] rates = dic.get(this.type_insurance);
                        double b = Math.random() * (rates[1] - rates[0]) + rates[0];
                        this.bet = Math.round(b * 1000.0) / 1000.0;
                    } else if (risk[n].equalsIgnoreCase("кража") || risk[n].equalsIgnoreCase("кражи") || risk[n].equalsIgnoreCase("воров")) {
                        this.type_insurance =  "Страхование имущества от кражи";
                        double[] rates = dic.get(this.type_insurance);
                        double b = Math.random() * (rates[1] - rates[0]) + rates[0];
                        this.bet = Math.round(b * 1000.0) / 1000.0;
                    }
                    break;

                case "груз", "товар":
                    if (risk[n].equalsIgnoreCase("повреждение") || risk[n].equalsIgnoreCase("повреждений") || risk[n].equalsIgnoreCase("повреждения")) {
                        this.type_insurance = "Страхование груза от повреждений";
                        double[] rates = dic.get(this.type_insurance);
                        double b = Math.random() * (rates[1] - rates[0]) + rates[0];
                        this.bet = Math.round(b * 1000.0) / 1000.0;
                    } else if (risk[n].equalsIgnoreCase("утрата") || risk[n].equalsIgnoreCase("утраты") || risk[n].equalsIgnoreCase("потери")
                            || risk[n].equalsIgnoreCase("потеря")) {
                        this.type_insurance = "Страхование груза от потери";
                        double[] rates = dic.get(this.type_insurance);
                        double b = Math.random() * (rates[1] - rates[0]) + rates[0];
                        this.bet = Math.round(b * 1000.0) / 1000.0;
                    }
                    break;

                case "путешествие":
                    if (risk[n].equalsIgnoreCase("болезнь") || risk[n].equalsIgnoreCase("болезни") || risk[n].equalsIgnoreCase("болезней")
                            || risk[n].equalsIgnoreCase("заболеваний") || risk[n].equalsIgnoreCase("травма") || risk[n].equalsIgnoreCase("травмы")
                            || risk[n].equalsIgnoreCase("повреждений") || risk[n].equalsIgnoreCase("вреда") || risk[n].equalsIgnoreCase("вред")) {
                        this.type_insurance = "Страхование путешествий (медицинские расходы)";
                        double[] rates = dic.get(this.type_insurance);
                        double b = Math.random() * (rates[1] - rates[0]) + rates[0];
                        this.bet = Math.round(b * 1000.0) / 1000.0;
                    } else if (risk[n].equalsIgnoreCase("отмена поездки") || risk[n].equalsIgnoreCase("отмена") || risk[n].equalsIgnoreCase("отмены")) {
                        this.type_insurance = "Страхование отмены поездки";
                        this.bet = 0.05;
                    }
                    break;

                case "животное", "питомец":
                    if (risk[n].equalsIgnoreCase("болезнь") || risk[n].equalsIgnoreCase("болезни") || risk[n].equalsIgnoreCase("болезней")
                            || risk[n].equalsIgnoreCase("заболеваний") || risk[n].equalsIgnoreCase("травмы") || risk[n].equalsIgnoreCase("повреждений")
                            || risk[n].equalsIgnoreCase("травм") || risk[n].equalsIgnoreCase("травма")) {
                        this.type_insurance = "Страхование здоровья животных";
                        double[] rates = dic.get(this.type_insurance);
                        double b = Math.random() * (rates[1] - rates[0]) + rates[0];
                        this.bet = Math.round(b * 1000.0) / 1000.0;
                    } else if (risk[n].equalsIgnoreCase("утрата") || risk[n].equalsIgnoreCase("потери") || risk[n].equalsIgnoreCase("потеря")
                            || risk[n].equalsIgnoreCase("утраты")) {
                        this.type_insurance = "Страхование животных от утраты";
                        double[] rates = dic.get(this.type_insurance);
                        double b = Math.random() * (rates[1] - rates[0]) + rates[0];
                        this.bet = Math.round(b * 1000.0) / 1000.0;
                    }
                    break;

                default:
                    System.out.println("Ошибка");
                    break;
            }

            if (type_insurance == null){
                System.out.println("Ошибка рисков страхование. Введите повторно");
                System.out.println("Мы можем предложить вам:\n Страхование автомобиля от: угона, повреждений, дтп, всего вместе;\n Страхование жизни/здоровья от: болезней, травм, производственного вреда, смерти;\n" +
                        " Страхование недвижимости от: пожара, наводнения или кражи; \n Страхование груза/товара от: повреждений или потери; \n Страхование животных от: " +
                        "болезней, травм, утраты; \n Страхование путешествий от: болезней, травм или отмены поездки.");
                System.out.println();
            }
        }
    }

    public double getSum() {
        return sum;
    }
    public void changeSum(){
        if (k == 3){
            System.out.println("К сожалению, мы не можем предложить вам другие условия договора. Если вас не устраивают условия, мы вынуждены с вами попрощаться.");
        } else{
            if (this.sum == this.first_sum){
                System.out.println("Сумма страхования - максимальная!");
            } else {
                double max = (this.first_sum - this.sum)/4;
                double min = (this.first_sum - this.sum)/16;
                double b = Math.random() * (max - min) + min;
                this.sum = this.sum + b;
            }
        }
        k++;
    }

    public void setSum() {
        Scanner scanner = new Scanner(System.in);
        int rand = (int) (Math.random() * state.length);  // Генерация случайного индекса
        String str = state[rand];
        while (first_sum == 0){
            try {
                switch (object) {
                    case "автомобиль", "машина", "недвижимость", "дом", "квартира", "груз", "товар":
                        System.out.print("Введите стоимость объекта: ");
                        this.first_sum = scanner.nextDouble();  // Может вызвать InputMismatchException
                        System.out.println("Состояние " + str);
                        break;
                    case "животное", "питомец":
                        System.out.print("Введите стоимость питомца: ");
                        this.first_sum = scanner.nextDouble();  // Может вызвать InputMismatchException
                        System.out.println("Состояние " + str);
                        break;
                    case "путешествие":
                        System.out.print("Введите стоимость поездки: ");
                        this.first_sum = scanner.nextDouble();  // Может вызвать InputMismatchException
                        str = "идеальное";
                        break;
                    case "жизнь", "здоровье":
                        this.first_sum = 3000000;
                        System.out.println("Состояние " + str);
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Ошибка. Пожалуйста, введите число больше 0.");
            }
        }


        switch (str){
            case "идеальное":
                this.sum = this.first_sum * 1;
                break;
            case "хорошее":
                this.sum = this.first_sum * 0.8;
                break;
            case "нормальное":
                this.sum = this.first_sum * 0.6;
                break;
            case "плохое":
                this.sum = this.first_sum * 0.4;
                break;
            case "ужасное":
                this.sum = this.first_sum * 0.2;
                break;
        }
    }

    public double getBet() {
        return bet;
    }
    public void setBet() {
        if (k == 3){
            System.out.println("К сожалению, мы не можем предложить вам другие условия договора. Если вас не устраивают условия, мы вынуждены с вами попрощаться.");
        } else {
            double[] rates = dic.get(this.type_insurance);
            double b = Math.random() * (this.bet - rates[0]) + rates[0];
            this.bet = Math.round(b * 10000.0) / 10000.0;
        }
        k++;
    }

    public double getPrice() {
        return price;
    }
    public void setPrice() {
        this.price = sum * bet;
    }

    public int getK() {
        return k;
    }

    public String getObject() {
        return object;
    }
    public void setObject(String object) {
        object = object.replaceAll("\\s+", "");
        switch (object.toLowerCase()) {
            case "автомобиль", "машина", "жизнь", "здоровье", "недвижимость", "дом", "квартира",
                    "груз", "товар", "путешествие", "животное":
                this.object = object;
                break;
            default:
                System.out.println("К сожалению, у нас нет такого страхования. Мы можем предложить вам застраховать недвижимость, " +
                        "автомобиль, жизнь, здоровье, груз, путешествие и животных");
        }
    }

    public void concludedAgreement(boolean payment){
        if (payment){
            System.out.println("Вот ваш договор №" + N + " от " + date);
            System.out.println("Всего хорошего! Досвидания ");
        } else {
            System.out.println("Оформление договора прервано. Досвидания!");
        }
    }

    public void displayInfo() {
        System.out.println();
        System.out.println("Договор составлен. Проверьте данные");
        System.out.println("Договор №" + N);
        System.out.println(type_insurance);
        String type2 = client_type.substring(0, client_type.length() - 2) + "ого";
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
        }
        System.out.println("Тарифная ставка: " + String.format("%.3f", bet*100) + "%");
        System.out.println("Сумма страхования: " + String.format("%.3f", sum));
        System.out.println("Итоговая стоимость договора: " + String.format("%.3f", price));
        System.out.println();
    }

    public void saveContract(Client client) {
        String sql = "INSERT INTO Contract (ID, Date, Sum_insured, Type_of_insurance, Tariff_rate, ID_branch, ID_person) VALUES (?, ?, ?, ?, ?, ?, ?)";
        int n = Main.LastID();
        int branch = (int)(Math.random() * n) + 1;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");
        String sqlDate = formatter.format(date);
        try (Connection conn = SQL.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, N);
            stmt.setString(2, sqlDate);
            stmt.setDouble(3, (double) Math.round(sum * 1000) /1000);
            stmt.setString(4, type_insurance);
            stmt.setDouble(5, bet*100);
            stmt.setInt(6, branch);
            stmt.setInt(7, client.getN());


            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
