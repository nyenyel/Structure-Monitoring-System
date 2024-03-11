package com.example.structuremonitoringsystem.Arduino;

public class Formula {

    private LowPassFilter filter;

    public Formula(float alpha) {
        this.filter = new LowPassFilter(alpha);
    }

    public float displacement(double velocityPerSec) {
        float filteredVelocity = filter.filter((float) velocityPerSec);

        // Define a threshold below which velocities are considered noise
        double velocityThreshold = 0.1; // Adjust as needed

        // Check if the filtered velocity exceeds the threshold
        if (Math.abs(filteredVelocity) <= velocityThreshold) {
            return 0f; // If filtered velocity is below the threshold, consider it as zero displacement
        } else {
            // Calculate displacement based on the filtered velocity
            float timeInterval = 0.1f; // Assuming a time interval of 0.1 seconds
            return filteredVelocity * timeInterval;
        }
    }


}
