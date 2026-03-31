package org.example.cybergaming.model;

import java.time.LocalDateTime;

public class Booking {
    private int id;
    private int userId;
    private int pcId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String status;
    private double totalCost;

    public Booking() {
    }

    public Booking(LocalDateTime endTime, int id, int pcId, LocalDateTime startTime, String status, double totalCost, int userId) {
        this.endTime = endTime;
        this.id = id;
        this.pcId = pcId;
        this.startTime = startTime;
        this.status = status;
        this.totalCost = totalCost;
        this.userId = userId;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPcId() {
        return pcId;
    }

    public void setPcId(int pcId) {
        this.pcId = pcId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "endTime=" + endTime +
                ", id=" + id +
                ", userId=" + userId +
                ", pcId=" + pcId +
                ", startTime=" + startTime +
                ", status='" + status + '\'' +
                ", totalCost=" + totalCost +
                '}';
    }
}