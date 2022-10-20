package edu.mirea.rksp.pr3.task4;

import java.util.UUID;

public class FileData {
    private String type;
    private int size;
    private UUID uuid;

    public FileData(String type, int size) {
        this.type = type;
        this.size = size;
        this.uuid = UUID.randomUUID();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public UUID getUuid() {
        return uuid;
    }

    @Override
    public String toString() {
        return "FileData{" +
                "type='" + type + '\'' +
                ", size=" + size +
                ", uuid=" + uuid +
                '}';
    }
}
