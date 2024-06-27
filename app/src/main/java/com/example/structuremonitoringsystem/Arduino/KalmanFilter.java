package com.example.structuremonitoringsystem.Arduino;

import java.util.ArrayList;

public class KalmanFilter {
    // State variables
    private float x;  // Estimated state (acceleration)
    private float P;  // Estimated error covariance

    // Process noise covariance (Q)
    private float Q;

    // Measurement noise covariance (R)
    private float R;

    // Constructor
    public KalmanFilter(float initialEstimate, float initialErrorCovariance, float processNoiseCovariance, float measurementNoiseCovariance) {
        this.x = initialEstimate;
        this.P = initialErrorCovariance;
        this.Q = processNoiseCovariance;
        this.R = measurementNoiseCovariance;
    }

    // Prediction step
    public void predict() {
        // Predict the next state based on the system dynamics (in this case, assume constant velocity)
        // x = x (no change in acceleration)
        // P = P + Q (increase in error covariance due to process noise)
        P += Q;
    }

    // Update step
    public void update(float z) {
        // Update the state estimate based on the measurement (accelerometer reading)
        // Kalman gain (K) = P / (P + R)
        float K = P / (P + R);

        // Update the state estimate
        x += K * (z - x);

        // Update the error covariance
        P *= (1 - K);
    }

    // Get the current state estimate
    public float getState() {
        return x;
    }

    // Set the process noise covariance
    public void setProcessNoiseCovariance(float processNoiseCovariance) {
        this.Q = processNoiseCovariance;
    }

    // Set the measurement noise covariance
    public void setMeasurementNoiseCovariance(float measurementNoiseCovariance) {
        this.R = measurementNoiseCovariance;
    }

    // Filter accelerometer data using ArrayList
    public ArrayList<Float> filter(ArrayList<Float> measurements) {
        ArrayList<Float> filteredData = new ArrayList<>();
        for (float measurement : measurements) {
            // Prediction step
            predict();

            // Update step
            update(measurement);

            // Get the filtered acceleration
            float filteredAcceleration = getState();

            // Add filtered acceleration to the result
            filteredData.add(filteredAcceleration);
        }
        return filteredData;
    }
}