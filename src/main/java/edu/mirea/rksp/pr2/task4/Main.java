package edu.mirea.rksp.pr2.task4;

import static edu.mirea.rksp.pr2.task3.Main.sum;
import static java.nio.file.StandardWatchEventKinds.OVERFLOW;
import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;

public class Main {

    public static void main(String[] args) {
        String currentDir = System.getProperty("user.dir");
        new File(currentDir+"/tmp").mkdir();
        Path tmpDir = Paths.get(currentDir, "tmp");
        System.out.println("Working directory is - "+tmpDir);

        try {
            WatchService watcher = FileSystems.getDefault().newWatchService();
            tmpDir.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);

            System.out.println("Watch Service registered for dir: " + tmpDir.getFileName());

            while (true) {
                WatchKey key;
                try {
                    key = watcher.take();
                } catch (InterruptedException ex) {
                    return;
                }

                for (WatchEvent<?> event : key.pollEvents()) {
                    WatchEvent.Kind<?> kind = event.kind();

                    @SuppressWarnings("unchecked")
                    WatchEvent<Path> ev = (WatchEvent<Path>) event;
                    Path fileName = ev.context();

                    System.out.println(kind.name() + ": " + fileName);

                    if (kind == OVERFLOW) {
                        continue;
                    } else if (kind == ENTRY_CREATE) {
                        // process create event
                    } else if (kind == ENTRY_DELETE) {
                        // process create event
                    } else if (kind == ENTRY_MODIFY) {
                        // process create event
                    }
                }

                boolean valid = key.reset();
                if (!valid) {
                    break;
                }
            }

        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
}
