package edu.mirea.rksp.pr3.task2;

import io.reactivex.rxjava3.core.Observable;

import java.io.Serializable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class Main2 {
    private static boolean isEnd = false;

    public static void main(String[] args) {
        Observable<Integer> observable1 = Observable
                .intervalRange(1, 10, 0, 500, TimeUnit.MILLISECONDS)
                .map(i -> getRandomNumber1());
        Observable<Integer> observable2 = Observable
                .intervalRange(1, 10, 15, 500, TimeUnit.MILLISECONDS)
                .map(i -> getRandomNumber2());

        Observable<Serializable> observableMain = Observable.merge(observable1, observable2);


        CountDownLatch latch = new CountDownLatch(1);

        observableMain.subscribe(System.out::print, System.out::print, latch::countDown);

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }




    private static int getRandomNumber(int min, int max) {
        return (int) Math.round(Math.random() * (max - min) + min);
    }
    private static int getRandomNumber1() {
        return getRandomNumber(0, 0);
    }
    private static int getRandomNumber2() {
        return getRandomNumber(9, 9);
    }
}
