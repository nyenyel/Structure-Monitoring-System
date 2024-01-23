package com.example.structuremonitoringsystem.Arduino;

public class Formula {

    public static double[] calculateEulerAngles(double[][] rotationMatrix) {
        // Extract individual rotation matrices
        double[][] rotationX = { {1, 0, 0}, {0, Math.cos(rotationMatrix[0][0]), -Math.sin(rotationMatrix[0][0])}, {0, Math.sin(rotationMatrix[0][0]), Math.cos(rotationMatrix[0][0])} };
        double[][] rotationY = { {Math.cos(rotationMatrix[1][1]), 0, Math.sin(rotationMatrix[1][1])}, {0, 1, 0}, {-Math.sin(rotationMatrix[1][1]), 0, Math.cos(rotationMatrix[1][1])} };
        double[][] rotationZ = { {Math.cos(rotationMatrix[2][2]), -Math.sin(rotationMatrix[2][2]), 0}, {Math.sin(rotationMatrix[2][2]), Math.cos(rotationMatrix[2][2]), 0}, {0, 0, 1} };

        // Compute Euler angles
        double pitch = Math.asin(rotationY[0][2]);
        double yaw = Math.atan2(-rotationX[1][2], rotationZ[1][1]);
        double roll = Math.atan2(-rotationY[0][1], rotationY[0][0]);

        // Convert angles to degrees if needed
        pitch = Math.toDegrees(pitch);
        yaw = Math.toDegrees(yaw);
        roll = Math.toDegrees(roll);

        return new double[]{roll, pitch, yaw};
    }


    public static void main(String[] args) {
        // Example usage
        double[][] rotationMatrix = {
                {0.866, -0.5, 0},
                {0.5, 0.866, 0},
                {0, 0, 1}
        };

        double[] eulerAngles = calculateEulerAngles(rotationMatrix);

        System.out.println("Roll: " + eulerAngles[0] + " degrees");
        System.out.println("Pitch: " + eulerAngles[1] + " degrees");
        System.out.println("Yaw: " + eulerAngles[2] + " degrees");
    }
}
