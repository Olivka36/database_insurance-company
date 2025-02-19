package org.example;


public class Application {
    // Поля класса (атрибуты)
    private String object;
    private String risks;
    private String type;
    private String name;
    private String telephone;

    private static int N = 0;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public Application() {
        this.object = null;
        this.risks = null;
        this.type = "Individual";
        this.name = null;
        this.telephone = null;
        N++;
    }
    // Конструктор с параметрами
    public Application(String object, String risks, String type, String name, String telephone) {
        this.object = object;
        this.risks = risks;
        this.type = type;
        this.name = name;
        this.telephone = telephone;
        N++;
    }

    public void submitApplication(){
        System.out.println("Заявка на страхование подана");
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("Заявка №" + N + ".\n");
        result.append("Объект: ").append(object).append("\n")
                .append("Риски: ").append(risks).append("\n")
                .append("Тип: ").append(type).append(" лицо \n");

        // Условие для ФИО или Наименования организации
        if ("Физическое".equalsIgnoreCase(type)) {
            result.append("ФИО: ").append(name).append("\n");
        } else if ("юридическое".equalsIgnoreCase(type)) {
            result.append("Наименование организации: ").append(name).append("\n");
        }

        String telephone2 = "8" + telephone;
        telephone2 = telephone2.replaceFirst("(\\d)(\\d{3})(\\d{3})(\\d{2})(\\d{2})", "$1 ($2) $3-$4-$5");

        result.append("Телефон: ").append(telephone2);
        return result.toString();
    }

}
