Система управління запасами для продуктового магазину
Цей проект спрямований на розробку веб-додатку для управління інвентарем продуктів у продуктовому магазині. Додаток автоматизує процес ведення обліку та управління запасами, що покращує ефективність та точність роботи працівників магазину.

Функціональні вимоги:
  
Функціонал для працівників:
-Перегляд списку всіх продуктів на складі.
-Детальний перегляд інформації про конкретний продукт.
-Пошук продуктів за назвою (частковий або повний збіг) і, за бажанням, за діапазоном цін.
-Додавання нових продуктів на склад.
-Редагування інформації про продукти.
-Видалення продуктів зі складу.
-Інформація про продукт:

Кожен продукт повинен містити:
-Назву
-Опис
-Країну виробника
-Ціну
-Кількість на складі

Як запустити додаток:

Для запуску додатка слід виконати наступні кроки:

1)Запуск бази даних PostgreSQL:
docker run --name postgres -e POSTGRES_PASSWORD=postgres -p 5432:5432 -d postgres
Ця команда запускає контейнер бази даних PostgreSQL з ім'ям postgres, паролем postgres і використовує порт 5432.

2)Запуск додатка:
Виконайте запуск додатка.

Використані технології:

Backend: Java, Spring Boot, Maven та додатково використав Flyway, Mockito, Lombok, SLf4J, open-api та modelmapper
База даних: PostgreSQL
