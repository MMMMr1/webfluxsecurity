Приложение с использованием следующих технологий:
•	Spring Boot 3
•	Spring Security (JWT)
•	Spring WebFlux
•	Spring Data R2DBC
•	MapStruct
•	PostgreSQL
•	Flyway

# Локальный запуск приложения

•	Установить PostgreSQL

# Создать БД

CREATE DATABASE "webflux_security";

# Установить корректные значения в application.yml

spring:r2dbc:username
spring:r2dbc:password

# cURL запросов:

1. Регистрация пользователя

![img_4.png](img_4.png)

Пример ответа:

![img_5.png](img_5.png)

2. Аутентификация пользователя

![img_6.png](img_6.png)

Пример ответа

![img_7.png](img_7.png)

3. Получение данных пользователя с использованием токена, полученного в предыдущем запросе

![img_8.png](img_8.png)

Пример ответа

![img.png](img.png)