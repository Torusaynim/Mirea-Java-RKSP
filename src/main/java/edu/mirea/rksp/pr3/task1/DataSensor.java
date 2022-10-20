package edu.mirea.rksp.pr3.task1;

public class DataSensor {
    private int temperature;
    private int co2;

    public DataSensor(int temperature, int co2) {
        this.temperature = temperature;
        this.co2 = co2;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public int getCo2() {
        return co2;
    }

    public void setCo2(int co2) {
        this.co2 = co2;
    }
}
