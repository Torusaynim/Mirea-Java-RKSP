package edu.mirea.rksp.pr3.task2;

import io.reactivex.rxjava3.core.Observable;

public class Main1 {
    public static void main(String[] args) {
        // Преобразовать поток из случайного количества (от 0 до 1000)
        // случайных чисел в поток, содержащий количество чисел.

        System.out.println("Поток количества чисел");
        Observable.range(1, (int) Math.round(Math.random() * 1000))
                .count()
                .subscribe(System.out::println);
    }
}
