package org.example.cybergaming.service;

import org.example.cybergaming.dao.DAOFactory;
import org.example.cybergaming.dao.interfaces.BookingDAO;
import org.example.cybergaming.dao.interfaces.FoodDAO;
import org.example.cybergaming.dao.interfaces.OrderDAO;
import org.example.cybergaming.dao.interfaces.PCDAO;
import org.example.cybergaming.dao.interfaces.UserDAO;
import org.example.cybergaming.model.Booking;
import org.example.cybergaming.model.Food;
import org.example.cybergaming.model.Order;
import org.example.cybergaming.model.PC;
import org.example.cybergaming.model.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class CustomerService {
    PCDAO pcDAO = DAOFactory.getPCDAO();
    BookingDAO bookingDAO = DAOFactory.getBookingDAO();
    OrderDAO orderDAO = DAOFactory.getOrderDAO();
    FoodDAO foodDAO = DAOFactory.getFoodDAO();
    UserDAO userDAO = DAOFactory.getUserDAO();

    // format nhập từ bàn phím
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // tìm máy trống
    public List<PC> findAvailablePC(
            int categoryId,
            String start,
            String end) {

        return pcDAO.getAvailablePCs(categoryId, start, end);
    }

    public List<Food> getFoodMenu() {
        return foodDAO.getAllFoods();
    }

    public List<Booking> getBookingsByUserId(int userId) {
        return bookingDAO.getBookingsByUserId(userId);
    }

    public double getCurrentBalance(int userId) {
        User user = userDAO.findById(userId);
        if (user == null) {
            return 0;
        }
        return user.getBalance();
    }

    public List<Order> getTransactionHistory(int userId) {
        return orderDAO.getOrdersByUserId(userId);
    }

    // đặt máy
    public int bookingPC(int userId, int pcId, String start, String end) {
        Booking booking = new Booking();
        PC pc = pcDAO.findById(pcId);
        if (pc == null) {
            System.out.println("Máy không tồn tại");
            return -1;
        }
        if (!"available".equalsIgnoreCase(pc.getStatus())) {
            System.out.println("Máy hiện không sẵn sàng để đặt");
            return -1;
        }

        LocalDateTime startTime;
        LocalDateTime endTime;
        try {
            startTime = LocalDateTime.parse(start, formatter);
            endTime = LocalDateTime.parse(end, formatter);
        } catch (DateTimeParseException e) {
            System.out.println("Sai định dạng thời gian. Dùng yyyy-MM-dd HH:mm:ss");
            return -1;
        }

        if (!startTime.isBefore(endTime)) {
            System.out.println("Thời gian không hợp lệ");
            return -1;
        }
        // trùng lịch
        if (bookingDAO.hasTimeConflict(pcId, startTime, endTime)) {
            System.out.println("Máy đã được đặt trong khung giờ này");
            return -1;
        }

        booking.setUserId(userId);
        booking.setPcId(pcId);
        booking.setStartTime(startTime);
        booking.setEndTime(endTime);
        booking.setStatus("pending");

        return bookingDAO.createBooking(booking);
    }

    // mua đồ ăn
    public boolean orderFood(int userId, int bookingId, int foodId, int quantity) {
        if (quantity <= 0) {
            System.out.println("Số lượng phải lớn hơn 0");
            return false;
        }
        Booking booking = bookingDAO.findById(bookingId);
        if (booking == null) {
            System.out.println("Booking không tồn tại");
            return false;
        }
        if (booking.getUserId() != userId) {
            System.out.println("Bạn chỉ được gọi món cho booking của chính mình");
            return false;
        }
        if ("completed".equalsIgnoreCase(booking.getStatus())) {
            System.out.println("Booking đã hoàn thành nên không thể gọi thêm món");
            return false;
        }
        Food food = foodDAO.findById(foodId);
        if (food == null) {
            System.out.println("Món ăn không tồn tại");
            return false;
        }
        if (food.getStock() < quantity) {
            System.out.println("Không đủ tồn kho");
            return false;
        }
        boolean decreased = foodDAO.decreaseStockIfEnough(foodId, quantity);
        if (!decreased) {
            System.out.println("Không thể giữ tồn kho cho món ăn này");
            return false;
        }
        Order order = new Order();
        order.setBookingId(bookingId);
        order.setFoodId(foodId);
        order.setQuantity(quantity);
        order.setTotalPrice(quantity * food.getPrice());
        int orderId = orderDAO.createOrder(order);
        if (orderId == -1) {
            System.out.println("Tạo order thất bại");
            return false;
        }
        return true;
    }
}