package edu.mirea.rksp.pr3.task1;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;

import java.util.Timer;
import java.util.TimerTask;


public class Sensor implements ObservableOnSubscribe<Integer> {
    private final int minValue;
    private final int maxValue;
    private final Timer timer;

    public Sensor(int minValue, int maxValue) {
        this.minValue = minValue;
        this.maxValue = maxValue;
        timer = new Timer();
    }

    @Override
    public void subscribe(@NonNull ObservableEmitter<Integer> emitter) {

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                emitter.onNext((int) Math.round(Math.random() * (maxValue - minValue) + minValue));
            }
        }, 0, 1000);
    }
}
