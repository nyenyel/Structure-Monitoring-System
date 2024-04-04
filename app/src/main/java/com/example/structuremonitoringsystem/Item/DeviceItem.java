package com.example.structuremonitoringsystem.Item;

public class DeviceItem {
    String deviceName, deviceID, sensorListID;

    public DeviceItem(String deviceName, String deviceID, String sensorListID) {
        this.deviceName = deviceName;
        this.deviceID = deviceID;
        this.sensorListID = sensorListID;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public String getSensorListID() {
        return sensorListID;
    }

    public void setSensorListID(String sensorListID) {
        this.sensorListID = sensorListID;
    }
}
