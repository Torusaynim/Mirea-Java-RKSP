package edu.mirea.rksp.pr3.task4;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;

import java.util.Timer;
import java.util.TimerTask;

public class GeneratorFile implements ObservableOnSubscribe<FileData> {
    private final String[] arrTypes;
    private final int MIN_SIZE;
    private final int MAX_SIZE;
    private final int MIN_PERIOD;
    private final int MAX_PERIOD;

    public GeneratorFile(String[] arrTypes, int MIN_SIZE, int MAX_SIZE, int MIN_PERIOD, int MAX_PERIOD) {
        this.arrTypes = arrTypes;
        this.MIN_SIZE = MIN_SIZE;
        this.MAX_SIZE = MAX_SIZE;
        this.MIN_PERIOD = MIN_PERIOD;
        this.MAX_PERIOD = MAX_PERIOD;
    }

    @Override
    public void subscribe(@NonNull ObservableEmitter<FileData> emitter) {
        generateSchedule(emitter);
    }

    private void generateSchedule(@NonNull ObservableEmitter<FileData> emitter) {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            public void run()
            {
                emitter.onNext(new FileData(generateType(), generateSize()));
                generateSchedule(emitter);
            }
        };

        timer.schedule(task, generatePeriod());
    }

    private String generateType() {
        int index = (int) Math.floor(Math.random()  * this.arrTypes.length);

        return this.arrTypes[index];
    }

    private int generateSize() {
        return generateNumberInRange(MIN_SIZE, MAX_SIZE);
    }

    private int generatePeriod() {
        return generateNumberInRange(MIN_PERIOD, MAX_PERIOD);
    }

    private int generateNumberInRange(int min, int max) {
        return min + (int) Math.round(Math.random() * (max - min));
    }
}
