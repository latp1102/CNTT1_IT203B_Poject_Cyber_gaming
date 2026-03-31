package org.example.cybergaming.model;

public class PC {
    private int id;
    private String pcNumber;
    private int categoryId;
    private String configuration;
    private double pricePerHour;
    private String status;

    public PC() {
    }

    public PC(int categoryId, String configuration, int id, String pcNumber, double pricePerHour, String status) {
        this.categoryId = categoryId;
        this.configuration = configuration;
        this.id = id;
        this.pcNumber = pcNumber;
        this.pricePerHour = pricePerHour;
        this.status = status;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getConfiguration() {
        return configuration;
    }

    public void setConfiguration(String configuration) {
        this.configuration = configuration;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPcNumber() {
        return pcNumber;
    }

    public void setPcNumber(String pcNumber) {
        this.pcNumber = pcNumber;
    }

    public double getPricePerHour() {
        return pricePerHour;
    }

    public void setPricePerHour(double pricePerHour) {
        this.pricePerHour = pricePerHour;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "PC{" +
                "categoryId=" + categoryId +
                ", id=" + id +
                ", pcNumber='" + pcNumber + '\'' +
                ", configuration='" + configuration + '\'' +
                ", pricePerHour=" + pricePerHour +
                ", status='" + status + '\'' +
                '}';
    }
}