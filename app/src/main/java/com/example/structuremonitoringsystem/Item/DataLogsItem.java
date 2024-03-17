package com.example.structuremonitoringsystem.Item;

public class DataLogsItem {
    String deviceId, sensorType, dates, times, xyzData;

    public DataLogsItem(String deviceId, String deviceType, String dates, String times, String xyzData) {
        this.deviceId = deviceId;
        this.sensorType = deviceType;
        this.dates = dates;
        this.times = times;
        this.xyzData = xyzData;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getSensorType() {
        return sensorType;
    }

    public void setSensorType(String deviceType) {
        this.sensorType = deviceType;
    }

    public String getDates() {
        return dates;
    }

    public void setDates(String dates) {
        this.dates = dates;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }

    public String getXyzData() {
        return xyzData;
    }

    public void setXyzData(String xyzData) {
        this.xyzData = xyzData;
    }
}
