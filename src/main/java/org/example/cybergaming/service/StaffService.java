package org.example.cybergaming.service;

import org.example.cybergaming.dao.DAOFactory;
import org.example.cybergaming.dao.interfaces.BookingDAO;
import org.example.cybergaming.dao.interfaces.OrderDAO;
import org.example.cybergaming.dao.interfaces.UserDAO;
import org.example.cybergaming.model.Booking;
import org.example.cybergaming.model.Order;

import java.util.List;

public class StaffService {
    BookingDAO bookingDAO = DAOFactory.getBookingDAO();
    OrderDAO orderDAO = DAOFactory.getOrderDAO();
    UserDAO userDAO = DAOFactory.getUserDAO();

    public void showAllBookings() {
        List<Booking> list = bookingDAO.getAllBookings();
        for (Booking b : list) {
            System.out.println(b);
        }
    }
//        boolean updated = bookingDAO.updateStatusIfCurrent(bookingId, "pending", "serving");

    public void startSession(int bookingId) {
        Booking booking = bookingDAO.findById(bookingId);
        if (booking == null) {
            System.out.println("Booking không tồn tại");
            return;
        }
        // Chấp nhận cả trạng thái 'pending' và 'confirmed'
        if (!"pending".equalsIgnoreCase(booking.getStatus()) && !"confirmed".equalsIgnoreCase(booking.getStatus())) {
            System.out.println("Không thể bắt đầu phiên. Booking không ở trạng thái chờ xác nhận hoặc đã xác nhận");
            return;
        }
        //
        boolean updated = bookingDAO.updateStatusIfCurrent(bookingId, booking.getStatus(), "serving");
        if (updated) {
            System.out.println("Đã chuyển booking sang trạng thái đang phục vụ");
        } else {
            System.out.println("Không thể bắt đầu phiên. Booking có thể đã được xử lý");
        }
    }

    public void finishSession(int bookingId) {
        Booking booking = bookingDAO.findById(bookingId);
        if (booking == null) {
            System.out.println("Booking không tồn tại");
            return;
        }
        if (!"serving".equalsIgnoreCase(booking.getStatus())) {
            System.out.println("Chỉ có thể kết thúc phiên khi booking đang ở trạng thái serving");
            return;
        }

        bookingDAO.updateStatus(bookingId, "completed");
        System.out.println("Đã kết thúc phiên chơi");
    }

    public void showPendingBookingRequests() {
        List<Booking> list = bookingDAO.getBookingsByStatus("pending");
        if (list.isEmpty()) {
            System.out.println("Không có yêu cầu đặt máy nào đang chờ xác nhận");
            return;
        }
        System.out.println("Danh sách yêu cầu đặt máy (pending):");
        for (Booking b : list) {
            System.out.println(b);
        }
    }

    public void showFoodRequests() {
        List<Order> pending = orderDAO.getOrdersByStatus("pending");
        List<Order> preparing = orderDAO.getOrdersByStatus("preparing");

        if (pending.isEmpty() && preparing.isEmpty()) {
            System.out.println("Không có yêu cầu đồ ăn nào");
            return;
        }

        System.out.println("Danh sách order pending:");
        for (Order order : pending) {
            System.out.println(order);
        }

        System.out.println("Danh sách order preparing:");
        for (Order order : preparing) {
            System.out.println(order);
        }
    }

    public void startPreparingOrder(int orderId) {
        boolean updated = orderDAO.updateOrderStatus(orderId, "preparing");
        if (updated) {
            System.out.println("Order đã chuyển sang trạng thái preparing");
        } else {
            System.out.println("Không thể cập nhật trạng thái order");
        }
    }

    public void completeOrder(int orderId, boolean chargeWallet) {
        Order order = orderDAO.findById(orderId);
        if (order == null) {
            System.out.println("Order không tồn tại");
            return;
        }

        if (!"preparing".equalsIgnoreCase(order.getStatus())) {
            System.out.println("Chỉ có thể hoàn thành order khi trạng thái là preparing");
            return;
        }
        if (chargeWallet) {
            Booking booking = bookingDAO.findById(order.getBookingId());
            if (booking == null) {
                System.out.println("Không tìm thấy booking của order");
                return;
            }
            boolean deducted = userDAO.decreaseBalanceIfEnough(booking.getUserId(), order.getTotalPrice());
            if (!deducted) {
                System.out.println("Không thể hoàn thành order do tài khoản không đủ số dư");
                return;
            }
        }
        boolean updated = orderDAO.updateOrderStatus(orderId, "done");
        if (updated) {
            System.out.println("Order đã hoàn thành");
        } else {
            System.out.println("Hoàn thành order thất bại");
        }
    }
}
