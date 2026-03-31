package org.example.cybergaming.presentation;

import org.example.cybergaming.model.User;
import org.example.cybergaming.service.AuthService;

import java.util.Scanner;

public class Main {
    static Scanner sc = new Scanner(System.in);
    static AuthService authService = new AuthService();
    public static void main(String[] args) {
        while (true){
            System.out.println("===== HỆ THỐNG CYBER GAMING VÀ DỊCH VỤ F&B =====");
            System.out.println("1. Đăng ký: ");
            System.out.println("2. Đăng nhập");
            System.out.println("0. Thoát chương trình");
            System.out.println("Nhập lựa chọn của bạn: ");
            try {
                int choice = sc.nextInt();
                sc.nextLine();
                switch (choice){
                    case 1:
                        register();
                        break;
                    case 2:
                        login();
                        break;
                    case 0:
                        System.out.println("Thoát chương trình");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Lựa chọn không hợp lệ");
                        break;
                }
            } catch (Exception e) {
                System.out.println("Lỗi: Vui lòng nhập số nguyên");
                sc.nextLine();
            }
        }
    }
    public static void register(){
        System.out.println("Nhập username: ");
        String u = sc.nextLine();

        System.out.println("Nhập password: ");
        String p = sc.nextLine();

        System.out.println("Nhập họ và tên: ");
        String fullName = sc.nextLine();

        System.out.println("Nhập số điện thoại: ");
        String phone = sc.nextLine();

        System.out.println("Nhập vai trò(admin/staff/customer): ");
        String r = sc.nextLine();
        if(authService.register(u, p,fullName, phone, r)){
            System.out.println("Đăng ký thành công");
        }else {
            System.out.println("Đăng ký thất bại");
        }
    }
    public static void login(){
        System.out.println("Nhập tên đăng nhập: ");
        String u = sc.nextLine();
        System.out.println("Nhập mật khẩu: ");
        String p = sc.nextLine();
        User user = authService.login(u, p);
        if (user==null){
            System.out.println("Đăng nhập thất bại");
            return;
        }
        System.out.println("Đăng nhập thành công");
        switch (user.getRole()){
            case "admin":
                AdminMenu.start();
                break;
            case "staff":
                StaffMenu.start();
                break;
            case "customer":
                CustomerMenu.start(user.getId());
                break;
            default:
                System.out.println("Vai trò không hợp lệ");
        }
    }
}
