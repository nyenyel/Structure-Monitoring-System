package com.example.structuremonitoringsystem.Arduino;

public class LowPassFilter {
    private float alpha;
    private float lastFilteredValue;

    public LowPassFilter(float alpha) {
        this.alpha = alpha;
        this.lastFilteredValue = 0;
    }

    public float filter(float rawValue) {
        float filteredValue = lastFilteredValue + alpha * (rawValue - lastFilteredValue);
        lastFilteredValue = filteredValue;
        return filteredValue;
    }

    public static void main(String[] args) {
        // Example usage
        LowPassFilter filter = new LowPassFilter(0.1f); // Adjust alpha as needed

        // Simulated accelerometer data
        float[] accelerometerData = {3, -3, 3, -3, 3}; // Example data

        for (float data : accelerometerData) {
            float filteredData = filter.filter(data);
            System.out.println("Filtered data: " + filteredData);
        }
    }
}
