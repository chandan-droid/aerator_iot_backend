package com.example.AeratorIoTBackend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "sensor_data")  // MongoDB collection name
public class SensorDataModel {

    @Id
    private String id;
    private String name;
    private double oxygenLevel;
    private double pHLevel;
    private double turbidity;
    private boolean isRunning;

    // Default constructor 
    public SensorDataModel() {
    }

    // All-args constructor
    public SensorDataModel(String id, String name, double oxygenLevel, double pHLevel, double turbidity, boolean isRunning) {
        this.id = id;
        this.name = name;
        this.oxygenLevel = oxygenLevel;
        this.pHLevel = pHLevel;
        this.turbidity = turbidity;
        this.isRunning = isRunning;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getOxygenLevel() {
        return oxygenLevel;
    }

    public void setOxygenLevel(double oxygenLevel) {
        this.oxygenLevel = oxygenLevel;
    }

    public double getpHLevel() {
        return pHLevel;
    }

    public void setpHLevel(double pHLevel) {
        this.pHLevel = pHLevel;
    }

    public double getTurbidity() {
        return turbidity;
    }

    public void setTurbidity(double turbidity) {
        this.turbidity = turbidity;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

    @Override
    public String toString() {
        return "SensorDataModel{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", oxygenLevel=" + oxygenLevel +
                ", pHLevel=" + pHLevel +
                ", turbidity=" + turbidity +
                ", isRunning=" + isRunning +
                '}';
    }
}

