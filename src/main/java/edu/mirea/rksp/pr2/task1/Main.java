package edu.mirea.rksp.pr2.task1;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static File createFile(final String filename, final String pathname, final long sizeInBytes) throws IOException {
        File file = new File(pathname + File.separator + filename);
        file.createNewFile();

        RandomAccessFile raf = new RandomAccessFile(file, "rw");
        raf.setLength(sizeInBytes);
        raf.close();

        return file;
    }

    public static void main(String[] args) throws IOException {
        String currentDir = System.getProperty("user.dir");
        new File(currentDir+"/tmp").mkdir();
        Path tmpDir = Paths.get(currentDir, "tmp");
        System.out.println("Working directory is - "+tmpDir);

        
    }
}
