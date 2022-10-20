package edu.mirea.rksp.pr3.task4;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

public class FileDataHandler implements Observer<FileData> {
    private final int PERIOD_MULTIPLIER;
    private final String TYPE_FILE;

    public FileDataHandler(int PERIOD_MULTIPLIER, String TYPE_FILE) {
        this.PERIOD_MULTIPLIER = PERIOD_MULTIPLIER;
        this.TYPE_FILE = TYPE_FILE;
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {
        System.out.println("onSubscribe " + this.getClass());
    }

    @Override
    public void onNext(@NonNull FileData fileData) {
        if (!fileData.getType().equals(this.TYPE_FILE)) {
            return;
        }

        Thread thread = new Thread(() -> {
            try {
                long sleepTime = (long) fileData.getSize() * this.PERIOD_MULTIPLIER;
                System.out.println("sleep: " + sleepTime);
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(fileData);
        });

        thread.start();
    }

    @Override
    public void onError(@NonNull Throwable e) {
        e.printStackTrace();
    }

    @Override
    public void onComplete() {
        System.out.println("onComplete " + this.getClass());
    }
}
