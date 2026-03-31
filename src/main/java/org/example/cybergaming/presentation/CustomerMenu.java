package org.example.cybergaming.presentation;

import org.example.cybergaming.model.Booking;
import org.example.cybergaming.model.Food;
import org.example.cybergaming.model.Order;
import org.example.cybergaming.model.PC;
import org.example.cybergaming.service.CustomerService;

import java.util.List;
import java.util.Scanner;

public class CustomerMenu {
    static Scanner sc = new Scanner(System.in);
    static CustomerService service = new CustomerService();
    public static void start(int userId) {
        while (true) {
            System.out.println("\n===== MENU KHÁCH HÀNG =====");
            System.out.println("1. Xem máy trống");
            System.out.println("2. Đặt máy");
            System.out.println("3. Gọi đồ ăn");
            System.out.println("4. Theo dõi booking của tôi");
            System.out.println("5. Xem số dư tài khoản");
            System.out.println("6. Xem lịch sử giao dịch");
            System.out.println("0. Quay lại menu chính");
            System.out.print("Chọn: ");
            try {
                int choice = sc.nextInt();
                sc.nextLine();
            switch (choice) {
                case 1:
                    showAvailablePC();
                    break;
                case 2:
                    bookingPC(userId);
                    break;
                case 3:
                    orderFood(userId);
                    break;
                case 4:
                    showMyBookings(userId);
                    break;
                case 5:
                    showCurrentBalance(userId);
                    break;
                case 6:
                    showTransactionHistory(userId);
                    break;
                case 0:
                    System.out.println("Quay lại menu chính");
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ");
                }
            } catch (Exception e) {
                System.out.println("Lỗi: Vui lòng nhập số");
                sc.nextLine();
            }

        }
    }
    // xem máy trống
    private static void showAvailablePC() {
        System.out.print("Nhập id khu vực (1=Standard,2=VIP): ");
        int cat = sc.nextInt();
        sc.nextLine();

        System.out.print("Thời gian bắt đầu (yyyy-mm-dd hh:mm:ss): ");
        String start = sc.nextLine();

        System.out.print("Thời gian kết thúc (yyyy-mm-dd hh:mm:ss): ");
        String end = sc.nextLine();
        List<PC> pcs = service.findAvailablePC(cat, start, end);
        System.out.println("Danh sách máy trống:");
        for (PC pc : pcs) {
            System.out.println(pc.getId() + " - " + pc.getPcNumber());
        }
    }
    // booking
    private static void bookingPC(int userId) {
        System.out.print("ID máy: ");
        int pcId = sc.nextInt();
        sc.nextLine();

        System.out.print("Thời gian bắt đầu: ");
        String start = sc.nextLine();

        System.out.print("Thời gian kết thúc: ");
        String end = sc.nextLine();

        int bookingId = service.bookingPC(userId, pcId, start, end);
        if (bookingId != -1){
            System.out.println("Đặt máy thành công. BookingID=" + bookingId);
        }
        else{
            System.out.println("Đặt máy thất bại");
        }
    }

    // order food
    private static void orderFood(int userId) {
        List<Booking> bookings = service.getBookingsByUserId(userId);
        if (bookings.isEmpty()) {
            System.out.println("Bạn chưa có booking nào");
            return;
        }
        System.out.println("Booking của bạn:");
        for (Booking booking : bookings) {
            System.out.println("BookingID=" + booking.getId()
                    + " | PC=" + booking.getPcId()
                    + " | " + booking.getStartTime()
                    + " -> " + booking.getEndTime());
        }
        List<Food> foods = service.getFoodMenu();
        if (foods.isEmpty()) {
            System.out.println("Menu hiện đang trống");
            return;
        }

        System.out.println("Menu món ăn/uống:");
        for (Food food : foods) {
            System.out.println(food.getId() + " - " + food.getName()
                    + " | Giá=" + food.getPrice()
                    + " | Tồn=" + food.getStock());
        }

        System.out.print("Booking ID: ");
        int bookingId = sc.nextInt();

        System.out.print("Food ID: ");
        int foodId = sc.nextInt();

        System.out.print("Số lượng: ");
        int qty = sc.nextInt();
        sc.nextLine();

        boolean success = service.orderFood(userId, bookingId, foodId, qty);
        if (success) {
            System.out.println("Đã gửi yêu cầu món ăn");
        } else {
            System.out.println("Gọi món thất bại");
        }
    }
    private static void showMyBookings(int userId) {
        List<Booking> bookings = service.getBookingsByUserId(userId);
        if (bookings.isEmpty()) {
            System.out.println("Bạn chưa có booking nào");
            return;
        }
        System.out.println("Danh sách booking của bạn:");
        for (Booking booking : bookings) {
            System.out.println("BookingID=" + booking.getId()
                    + " | PC=" + booking.getPcId()
                    + " | Status=" + booking.getStatus()
                    + " | " + booking.getStartTime()
                    + " -> " + booking.getEndTime());
        }
    }
    private static void showCurrentBalance(int userId) {
        double balance = service.getCurrentBalance(userId);
        System.out.println("Số dư hiện tại: " + balance);
    }
    private static void showTransactionHistory(int userId) {
        List<Order> orders = service.getTransactionHistory(userId);
        if (orders.isEmpty()) {
            System.out.println("Chưa có giao dịch nào");
            return;
        }
        System.out.println("Lịch sử giao dịch (orders):");
        for (Order order : orders) {
            System.out.println("OrderID=" + order.getId()
                    + " | BookingID=" + order.getBookingId()
                    + " | FoodID=" + order.getFoodId()
                    + " | Qty=" + order.getQuantity()
                    + " | Total=" + order.getTotalPrice()
                    + " | Status=" + order.getStatus());
        }
    }
}