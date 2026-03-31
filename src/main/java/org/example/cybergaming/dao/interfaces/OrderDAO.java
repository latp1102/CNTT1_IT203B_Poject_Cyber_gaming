package org.example.cybergaming.dao.interfaces;

import org.example.cybergaming.model.Order;
import java.util.List;

public interface OrderDAO {
    // Tạo order mới
    int createOrder(Order order);

    // Lấy tất cả orders
    List<Order> getAllOrders();
    // Lấy orders theo trạng thái
    List<Order> getOrdersByStatus(String status);
    // Lấy orders theo booking ID
    List<Order> getOrdersByBookingId(int bookingId);
    // Lấy lịch sử orders của một user (qua booking)
    List<Order> getOrdersByUserId(int userId);
    // cập nhật trạng thái
    boolean updateOrderStatus(int orderId, String status);
    // tìm order id
    Order findById(int id);
    // xóa theo id
    boolean deleteOrder(int id);
}
