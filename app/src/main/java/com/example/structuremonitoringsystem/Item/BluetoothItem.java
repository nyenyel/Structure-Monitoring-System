package com.example.structuremonitoringsystem.Item;

public class BluetoothItem {

    String deviceName;
    String deviceMac;

    public BluetoothItem(String deviceName, String deviceMac) {
        this.deviceName = deviceName;
        this.deviceMac = deviceMac;
    }
    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceMac() {
        return deviceMac;
    }

    public void setDeviceMac(String deviceMac) {
        this.deviceMac = deviceMac;
    }





}
