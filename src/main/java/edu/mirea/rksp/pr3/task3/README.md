# Задание 3
Реализовать класс UserFriend. Поля — int userId, friendId. Заполнить массив
объектов UserFriend случайными данными.

Реализовать функцию: Observable<UserFriend> getFriends(int userId),
возвращающую поток объектов UserFriend, по заданному userId. (Для
формирования потока из массива возможно использование функции
Observable.fromArray(T[] arr)).

Дан массив из случайных userId. Сформировать поток из этого массива.
Преобразовать данный поток в поток объектов UserFriend. Обязательно
получение UserFriend через функцию getFriends.
