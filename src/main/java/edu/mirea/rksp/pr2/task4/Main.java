package edu.mirea.rksp.pr2.task4;

import static edu.mirea.rksp.pr2.task3.Main.sum;
import static java.nio.file.StandardWatchEventKinds.OVERFLOW;
import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.*;

public class Main {

    public static void main(String[] args) {
        var path = "tmp";
        String currentDir = System.getProperty("user.dir");
        new File(currentDir+File.separator+path).mkdir();
        Path tmpDir = Paths.get(currentDir, path);
        System.out.println("Working directory is - "+tmpDir);

        try {
            WatchService watcher = FileSystems.getDefault().newWatchService();
            // creating reserve folder
            var reservePath = ".reserve";
            Reserve(tmpDir.getFileName().toString(), reservePath);
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
                        CreateBak(Paths.get(path, fileName.toString()),
                                Paths.get(reservePath, fileName.toString()));
                    } else if (kind == ENTRY_DELETE) {
                        sum(Paths.get(reservePath, fileName.toString() + ".bak").toFile());
                        DeleteBakFile(Paths.get(reservePath, fileName.toString() + ".bak"));
                    } else if (kind == ENTRY_MODIFY) {
                        Diff(Paths.get(path, fileName.toString()),
                                Paths.get(reservePath, fileName.toString() + ".bak"));
                        CreateBak(Paths.get(path, fileName.toString()),
                                Paths.get(reservePath, fileName.toString()));
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

    private static void Reserve(String path, String reservePath) {
        CreateDirectory(reservePath);
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                DeleteDirectory(reservePath);
            }
        });
        CopyFolder(path, reservePath);
    }

    private static void CopyFolder(String path, String reservePath) {
        var files = new File(path).listFiles();
        for (var file : files) {
            if (file.isDirectory()) {
                CopyFolder(file.getAbsolutePath(), reservePath);
            } else {
                var reserveFile = new File(reservePath + File.separator + file.getName() + ".bak");
                try {
                    reserveFile.createNewFile();
                    var fis = new FileInputStream(file);
                    var fos = new FileOutputStream(reserveFile);
                    var buffer = new byte[1024];
                    int length;
                    while ((length = fis.read(buffer)) > 0) {
                        fos.write(buffer, 0, length);
                    }
                    fis.close();
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // Compare the two files and print added or deleted lines with line numbers
    private static void Diff(Path path, Path reservePath) {
        try {
            var fis = new FileInputStream(path.toFile());
            var fisReserve = new FileInputStream(reservePath.toFile());
            var buffer = new byte[1024];
            var bufferReserve = new byte[1024];
            int length;
            int lengthReserve;
            int line = 1;
            while ((length = fis.read(buffer)) > 0 && (lengthReserve = fisReserve.read(bufferReserve)) > 0) {
                var str = new String(buffer, 0, length);
                var strReserve = new String(bufferReserve, 0, lengthReserve);
                var lines = str.split("\\r?\\n");
                var linesReserve = strReserve.split("\\r?\\n");
                var i = 0;
                var j = 0;
                while (i < lines.length && j < linesReserve.length) {
                    if (!lines[i].equals(linesReserve[j])) {
                        System.out.println("Line " + line + " added: " + lines[i]);
                        j++;
                    } else {
                        i++;
                        j++;
                    }
                    line++;
                }
                while (i < lines.length) {
                    System.out.println("Line " + line + " added: " + lines[i]);
                    i++;
                    line++;
                }
                while (j < linesReserve.length) {
                    System.out.println("Line " + line + " deleted: " + linesReserve[j]);
                    j++;
                    line++;
                }
            }
            fis.close();
            fisReserve.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void CreateBak(Path path, Path reservePath) {
        try {
            java.nio.file.Files.copy(path, reservePath.resolveSibling(
                    reservePath.getFileName() + ".bak"), java.nio.file.StandardCopyOption.REPLACE_EXISTING);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void DeleteBakFile(Path path) {
        try {
            java.nio.file.Files.delete(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void CreateDirectory(String name) {
        File file = new File(name);
        if (!file.exists()) {
            if (file.mkdir()) {
                System.out.println("Created reserve folder for monitoring.");
            } else {
                System.out.println("Failed to create reserve folder for monitoring.");
            }
        }
    }

    private static void DeleteDirectory(String name) {
        CleanDirectory(name);
        File file = new File(name);
        if (file.exists()) {
            if (file.delete()) {
                System.out.println("Stop monitoring...");
            } else {
                System.out.println("Failed to delete reserve folder for monitoring.");
            }
        }
    }

    private static void CleanDirectory(String name) {
        File file = new File(name);
        if (file.exists()) {
            File[] files = file.listFiles();
            for (File f : files) {
                if (f.isFile()) {
                    f.delete();
                }
            }
        }
    }
}
