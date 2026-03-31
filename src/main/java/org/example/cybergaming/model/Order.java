package org.example.cybergaming.model;

public class Order {
    private int id;
    private int bookingId;
    private int foodId;
    private int quantity;
    private double totalPrice;
    private String status;

    public Order() {
    }

    public Order(int bookingId, int foodId, int id, int quantity, String status, double totalPrice) {
        this.bookingId = bookingId;
        this.foodId = foodId;
        this.id = id;
        this.quantity = quantity;
        this.status = status;
        this.totalPrice = totalPrice;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "Order{" +
                "bookingId=" + bookingId +
                ", id=" + id +
                ", foodId=" + foodId +
                ", quantity=" + quantity +
                ", totalPrice=" + totalPrice +
                ", status='" + status + '\'' +
                '}';
    }
}