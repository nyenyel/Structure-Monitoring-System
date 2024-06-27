package com.example.structuremonitoringsystem.Item;

public class SensorItem {
    String sensorName, id, log;

    public SensorItem(String sensorName, String id, String log) {
        this.sensorName = sensorName;
        this.id = id;
        this.log = log;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public String getSensorName() {
        return sensorName;
    }

    public void setSensorName(String sensorName) {
        this.sensorName = sensorName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
