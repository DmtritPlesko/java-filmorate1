# Проект JavaFilmorate
Добро пожаловать в наш проект — уникальную социальную сеть, где любители кино могут находить друг друга и делиться своими предпочтениями!

## Описание проекта

Наша платформа позволяет пользователям:

- **Лайкать фильмы**: Выразите свои эмоции и предпочтения, ставя лайки на понравившиеся фильмы.
- **Добавлять друзей**: Находите единомышленников и создавайте свою сеть киноманов, чтобы обсуждать фильмы и обмениваться рекомендациями.
- **Получать список рекомендаций**: Всегда будет что посмотреть!
И еще много всего!

## Фильмы

Каждый фильм на нашей платформе имеет подробную информацию, например, описание, жанр, режиссера и так далее

## Схема базы данных

[Схема базы данных](diagram.svg)

## Пример данных и запросов

<details>
  <summary>Примеры запросов для работы с базой данных</summary>

  ### Запрос для получения заявок в друзья, отправленных пользователем (не принятых)
  ```sql
SELECT u.name
FROM users u
JOIN friends f ON u.user_id = f.friend_user_id
WHERE f.user_id = 1
AND NOT EXISTS (
    SELECT 1
    FROM friends f2
    WHERE f2.user_id = f.friend_user_id AND f2.friend_user_id = f.user_id
);
```
### Запрос для получения друзей пользователя
  ```sql
SELECT u.name
FROM users u
JOIN friends f ON u.user_id = f.friend_user_id
WHERE f.user_id = 1
AND EXISTS (
    SELECT 1
    FROM friends f2
    WHERE f2.user_id = f.friend_user_id AND f2.friend_user_id = f.user_id
);
```
### Запрос для получения всех запросов в друзья, которые пользователь должен подтвердить
  ```sql
SELECT u.name
FROM users u
JOIN friends f ON u.user_id = f.user_id
WHERE f.friend_user_id = 1
AND NOT EXISTS (
    SELECT 1
    FROM friends f2
    WHERE f2.user_id = f.friend_user_id AND f2.friend_user_id = f.user_id
);
```
### Запрос для получения всех фильмов, понравившихся пользователю
  ```sql
SELECT f.Name
FROM film f
JOIN likes u ON u.film_id = f.film_id
WHERE u.user_id = 1
```
### Запрос для получения всех пользователей, лайкнувших фильм
  ```sql
SELECT u.Name
FROM users u
JOIN likes ul ON ul.user_id = u.user_id
WHERE ul.film_id = 1
```
### Запрос для получения жанра фильма
  ```sql
SELECT g.Name
FROM genres g
JOIN genre_film gl ON gl.genre_id = g.genre_id
WHERE gl.film_id = 1
```
</details>
