package org.example.cybergaming.presentation;

import org.example.cybergaming.service.StaffService;

import java.util.Scanner;

public class StaffMenu {

    static Scanner sc = new Scanner(System.in);
    static StaffService service = new StaffService();

    public static void start() {
        while (true) {
            System.out.println("\n===== MENU NHÂN VIÊN =====");
            System.out.println("1. Xem yêu cầu đặt máy (pending)");
            System.out.println("2. Xác nhận booking -> đang phục vụ");
            System.out.println("3. Xem yêu cầu đồ ăn");
            System.out.println("4. Chuyển order -> preparing");
            System.out.println("5. Hoàn thành order");
            System.out.println("6. Kết thúc phiên chơi (booking -> completed)");
            System.out.println("7. Xem tất cả booking");
            System.out.println("0. Quay lại menu chính: ");
            System.out.print("Nhập lựa chọn của bạn: ");
            try {
                int choice = sc.nextInt();
                switch (choice) {
                    case 1:
                        service.showPendingBookingRequests();
                        break;
                    case 2:
                        System.out.print("Nhập booking id (pending): ");
                        service.startSession(sc.nextInt());
                        break;
                    case 3:
                        service.showFoodRequests();
                        break;
                    case 4:
                        System.out.print("Nhập order id: ");
                        service.startPreparingOrder(sc.nextInt());
                        break;
                    case 5:
                        System.out.print("Nhập order id: ");
                        int orderId = sc.nextInt();
                        System.out.print("Trừ tiền ví khi hoàn thành? (y/n): ");
                        String walletChoice = sc.next().trim();
                        boolean chargeWallet = "y".equalsIgnoreCase(walletChoice);
                        service.completeOrder(orderId, chargeWallet);
                        break;
                    case 6:
                        System.out.print("Nhập booking id: ");
                        service.finishSession(sc.nextInt());
                        break;
                    case 7:
                        service.showAllBookings();
                        break;
                    case 0:
                        System.out.println("Quay lại menu chính");
                        return;
                }
            } catch (Exception e) {
                System.out.println("Loi");
                sc.nextLine();
            }
        }
    }
}