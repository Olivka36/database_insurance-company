### Using a database in an insurance company   
### Имитация работы страховой компании, в особенности - заключение договора страхования.

![SQLite](https://img.shields.io/badge/SQLite-003B57?style=for-the-badge&logo=sqlite&logoColor=white)  ![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white) 

Для создания консольного приложения, имитирующего работу страховой компании, была спроектирована небольшая информационная система.🗃

## 📝 Этапы проектирования:
1️⃣ [ER-диаграмма](er.png) - определение сущностей, атрибутов и связей   
2️⃣ [Диаграмма IDEF0](idef0.png) - моделирования бизнес-процессов и функциональных требований   
3️⃣ [Диаграмма прецедентов](use_case.png) - визуализация сценариев взаимодействия между пользователем и системой   
4️⃣ [Диаграмма последовательностей и концептуальная модель](sequence.png) - моделирования последовательности сообщений между объектами в процессе заключения договора    
5️⃣ [Диаграмма сотрудничества](cooperation.png) - отображение сотрудничества объектов и компонентов системы для выполнения задачи   
6️⃣ [Диаграмма классов](class.png) - проектирование структуры приложения, включая классы, их атрибуты, методы и отношения между классами   

## 🗂 Структура программы
На основе диаграмм классов и сотрудничества была разработана программа, имитирующая процесс заключения страхового договора. Также на основе ER-диаграммы была создана база данных для хранения информации о клиентах, филиалах и договорах. В соответствии с диаграммой классов, программа включает следующие классы:
* ***Client*** - отображает информацию о клиенте с его атрибутами: имя, номер телефона, тип (физическое или юридическое лицо), объект для страхования и страхуемые риски.
* ***Application*** - класс для формирования заявки.
* ***Contract*** - класс для составления договора.
* ***Payment*** - класс, имитирующий оплату для заключения договора.
* ***SQL*** и ***DisplayDatabase*** - системные классы для подключения базы данных и отображения таблиц.

## 🚀 Функциональность программы:
- Отображение меню взаимодействия с программой для удобства пользователя.
- Просмотр баз данных клиентов, договоров и филиалов.
- Добавление новых филиалов в базу данных.
- Моделирование процесса заключения договора страхования с клиентом, включая добавление клиента в базу данных и внесение данных о договоре при его заключении.

## 😕 Недостатки: 
Для удаления клиентов, договоров и филиалов из БД необходимо использовать язык SQL.
