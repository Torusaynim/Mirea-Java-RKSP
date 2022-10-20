package edu.mirea.rksp.pr3.task4;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class FileQueue implements Observer<FileData>, ObservableOnSubscribe<FileData> {
    private final int LENGTH_QUEUE;
    private final ArrayList<FileData> fileDataArrayList;

    public FileQueue(int LENGTH_QUEUE) {
        this.LENGTH_QUEUE = LENGTH_QUEUE;
        this.fileDataArrayList = new ArrayList<>();
    }

    @Override
    public void subscribe(@NonNull ObservableEmitter<FileData> emitter) throws Throwable {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            public void run()
            {
                if (!fileDataArrayList.isEmpty()) {
                    emitter.onNext(fileDataArrayList.remove(0));
                }
            }
        };

        timer.schedule(task, 0, 10);
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {
        System.out.println("onSubscribe " + this.getClass());
    }

    @Override
    public void onNext(@NonNull FileData fileData) {
        if (fileDataArrayList.size() < LENGTH_QUEUE) {
            fileDataArrayList.add(fileData);
        }
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
