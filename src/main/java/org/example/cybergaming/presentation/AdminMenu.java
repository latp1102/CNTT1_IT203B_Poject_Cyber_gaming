package org.example.cybergaming.presentation;

import org.example.cybergaming.dao.DAOFactory;
import org.example.cybergaming.dao.interfaces.FoodDAO;
import org.example.cybergaming.dao.interfaces.PCDAO;
import org.example.cybergaming.dao.interfaces.UserDAO;
import org.example.cybergaming.model.Food;
import org.example.cybergaming.model.PC;
import org.example.cybergaming.model.User;

import java.util.List;
import java.util.Scanner;

public class AdminMenu {
    static Scanner sc = new Scanner(System.in);

    public static void start() {
        while (true) {
            System.out.println("===== MENU QUẢN TRỊ =====");
            System.out.println("1. Thêm máy trạm");
            System.out.println("2. Thêm đồ ăn/ nước uống");
            System.out.println("3. Xem danh sách máy trạm");
            System.out.println("4. Xem danh sách đồ ăn");
            System.out.println("5. Xem danh sách users");
            System.out.println("6. Sửa đồ ăn");
            System.out.println("7. Xóa đồ ăn");
            System.out.println("8. Xóa user");
            System.out.println("9. Sửa máy");
            System.out.println("10. Xóa máy");
            System.out.println("0. Quay lại menu chính");
            System.out.println("Nhập lựa chọn của bạn: ");
            try {
                int choice = sc.nextInt();
                sc.nextLine();
                switch (choice) {
                    case 1:
                        addPC();
                        break;
                    case 2:
                        addFood();
                        break;
                    case 3:
                        viewAllPCs();
                        break;
                    case 4:
                        viewAllFoods();
                        break;
                    case 5:
                        viewAllUsers();
                        break;
                    case 6:
                        updateFood();
                        break;
                    case 7:
                        deleteFood();
                        break;
                    case 8:
                        deleteUser();
                        break;
                    case 9:
                        updatePC();
                        break;
                    case 10:
                        deletePC();
                        break;
                    case 0:
                        System.out.println("Quay lại menu chính");
                        return;
                    default:
                        System.out.println("Lựa chọn không hợp lệ");
                }
            } catch (Exception e) {
                System.out.println("Lỗi: Vui lòng nhập số nguyên");
                sc.nextLine();
            }
        }
    }

    public static void addPC() {
        PC pc = new PC();
        System.out.println("Nhập số máy: ");
        pc.setPcNumber(sc.nextLine());

        System.out.println("Nhập id khu vực (1=standard, 2=vip): ");
        try {
            pc.setCategoryId(sc.nextInt());
            sc.nextLine();
        } catch (Exception e) {
            System.out.println("Lỗi: Phải nhập số nguyên cho ID khu vực!");
            sc.nextLine();
            return;
        }

        System.out.println("Nhập cấu hình máy: ");
        pc.setConfiguration(sc.nextLine());

        System.out.println("Nhập giá tiền mỗi giờ: ");
        try {
            pc.setPricePerHour(sc.nextDouble());
            sc.nextLine();
        } catch (Exception e) {
            System.out.println("Lỗi: Phải nhập số cho giá tiền");
            sc.nextLine();
            return;
        }
        DAOFactory.getPCDAO().addPC(pc);
        System.out.println("Thêm máy trạm thành công");
    }

    public static void addFood() {
        Food food = new Food();
        System.out.println("Nhập tên món: ");
        food.setName(sc.nextLine());

        System.out.println("Nhập mô tả: ");
        food.setDescription(sc.nextLine());

        System.out.println("Nhập giá tiền: ");
        food.setPrice(sc.nextDouble());

        System.out.println("Nhập số lượng tồn kho");
        food.setStock(sc.nextInt());
        DAOFactory.getFoodDAO().addFood(food);
        System.out.println("Thêm đồ ăn thành công");
    }

    public static void viewAllPCs() {
        System.out.println("\n===== DANH SÁCH MÁY TRẠM =====");
        PCDAO pcDAO = DAOFactory.getPCDAO();
        List<PC> pcs = pcDAO.getAllPCs();

        if (pcs.isEmpty()) {
            System.out.println("Không có máy trạm nào");
            return;
        }

        System.out.printf("|%-5s %-15s %-10s %-20s %-15s %-10s|\n", "ID", "Số máy", "Khu vực", "Cấu hình", "Giá/giờ", "Trạng thái");
        System.out.println("-".repeat(80));
        // lặp 80 dấu -

        for (PC pc : pcs) {
            System.out.printf("|%-5d %-15s %-10d %-20s %-15.0f %-10s|\n",
                    pc.getId(), pc.getPcNumber(), pc.getCategoryId(),
                    pc.getConfiguration(), pc.getPricePerHour(), pc.getStatus());
        }
    }

    public static void viewAllFoods() {
        System.out.println("\n===== DANH SÁCH ĐỒ ĂN/NƯỚC UỐNG =====");
        FoodDAO foodDAO = DAOFactory.getFoodDAO();
        List<Food> foods = foodDAO.getAllFoods();

        if (foods.isEmpty()) {
            System.out.println("Không có món nào");
            return;
        }
        System.out.printf("|%-5s %-25s %-30s %-10s %-10s|\n", "ID", "Tên món", "Mô tả", "Giá", "Tồn kho");
        System.out.println("-".repeat(85));
        for (Food food : foods) {
            System.out.printf("|%-5d %-25s %-30s %-10.0f %-10d|\n",
                    food.getId(), food.getName(), food.getDescription(),
                    food.getPrice(), food.getStock());
        }
    }

    public static void viewAllUsers() {
        System.out.println("\n===== DANH SÁCH USERS =====");
        UserDAO userDAO = DAOFactory.getUserDAO();
        List<User> users = userDAO.getAllUsers();

        if (users.isEmpty()) {
            System.out.println("Không có user nào");
            return;
        }
        System.out.printf("%-5s %-20s %-15s\n", "ID", "Username", "Role");
        System.out.println("-".repeat(45));
        for (User user : users) {
            System.out.printf("|%-5d %-20s %-15s|\n", user.getId(), user.getUsername(), user.getRole());
        }
    }
    public static void updateFood() {
        System.out.print("Nhập ID món cần sửa: ");
        int id = sc.nextInt();
        sc.nextLine();

        FoodDAO foodDAO = DAOFactory.getFoodDAO();
        Food food = foodDAO.findById(id);

        if (food == null) {
            System.out.println("Không tìm thấy món ăn");
            return;
        }

        System.out.println("Thông tin hiện tại: " + food);

        System.out.print("Nhập tên mới (để trống nếu giữ nguyên): ");
        String name = sc.nextLine();
        if (!name.isEmpty()){
            food.setName(name);
        }
        System.out.print("Nhập mô tả mới (để trống nếu giữ nguyên): ");
        String desc = sc.nextLine();
        if (!desc.isEmpty()){
            food.setDescription(desc);
        }
        System.out.print("Nhập giá mới (nhập 0 nếu giữ nguyên): ");
        double price = sc.nextDouble();
        if (price > 0){
            food.setPrice(price);
        }
        System.out.print("Nhập số lượng tồn kho mới (nhập -1 nếu giữ nguyên): ");
        int stock = sc.nextInt();
        if (stock >= 0){
            food.setStock(stock);
        }
        if (foodDAO.updateFood(food)) {
            System.out.println("Cập nhật thành công");
        } else {
            System.out.println("Cập nhật thất bại");
        }
    }

    public static void deleteFood() {
        System.out.print("Nhập ID món cần xóa: ");
        int id = sc.nextInt();
        sc.nextLine();

        FoodDAO foodDAO = DAOFactory.getFoodDAO();
        if (foodDAO.deleteFood(id)) {
            System.out.println("Xóa món thành công");
        } else {
            System.out.println("Xóa món thất bại");
        }
    }

    public static void deleteUser() {
        System.out.print("Nhập ID user cần xóa: ");
        int id = sc.nextInt();
        sc.nextLine();

        UserDAO userDAO = DAOFactory.getUserDAO();
        if (userDAO.deleteUser(id)) {
            System.out.println("Xóa user thành công");
        } else {
            System.out.println("Xóa user thất bại");
        }
    }

    public static void deletePC() {
        System.out.print("Nhập ID máy cần xóa: ");
        int id = sc.nextInt();
        sc.nextLine();
        PCDAO pcDAO = DAOFactory.getPCDAO();
        if (pcDAO.deletePC(id)) {
            System.out.println("Xóa máy thành công");
        } else {
            System.out.println("Xóa máy thất bại");
        }
    }

    public static void updatePC() {
        System.out.print("Nhập ID máy cần sửa: ");
        int id = sc.nextInt();
        sc.nextLine();

        PCDAO pcDAO = DAOFactory.getPCDAO();
        PC pc = pcDAO.findById(id);

        if (pc == null) {
            System.out.println("Không tìm thấy máy");
            return;
        }
        System.out.println("Thông tin hiện tại: " + pc);

        System.out.print("Số máy mới (Enter giữ nguyên): ");
        String pcNumber = sc.nextLine().trim();
        if (!pcNumber.isEmpty()){
            pc.setPcNumber(pcNumber);
        }
        System.out.print("Category mới (1=Standard,2=VIP nhập 0 giữ nguyên): ");
        int categoryId = sc.nextInt();
        sc.nextLine();
        if (categoryId > 0){
            pc.setCategoryId(categoryId);
        }
        System.out.print("Cấu hình mới (Enter giữ nguyên): ");
        String cfg = sc.nextLine().trim();
        if (!cfg.isEmpty()){
            pc.setConfiguration(cfg);
        }
        System.out.print("Giá/giờ mới (nhập 0 giữ nguyên): ");
        double price = sc.nextDouble();
        sc.nextLine();
        if (price > 0){
            pc.setPricePerHour(price);
        }
        System.out.print("Trạng thái mới (available/in_use/maintenance, Enter giữ nguyên): ");
        String status = sc.nextLine().trim();
        if (!status.isEmpty())
            pc.setStatus(status);
        if (pcDAO.updatePC(pc)) {
            System.out.println("Cập nhật máy thành công");
        } else {
            System.out.println("Cập nhật máy thất bại");
        }
    }
}
