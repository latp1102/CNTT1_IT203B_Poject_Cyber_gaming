package org.example.cybergaming.dao.interfaces;

import org.example.cybergaming.model.User;
import java.util.List;

public interface UserDAO {
    // Đăng ký người dùng mới
    boolean registerUser(User user);
    // Đăng nhập người dùng
    User login(String username);
    // Kiểm tra username đã tồn tại chưa
    boolean isUsernameExists(String username);
    // Lấy danh sách tất cả users
    List<User> getAllUsers();
    // Xóa user theo ID
    boolean deleteUser(int id);
    // Tìm user theo ID
    User findById(int id);
    // Cập nhật thông tin user
    boolean updateUser(User user);
    // Trừ tiền trong tài khoản nếu đủ số dư
    boolean decreaseBalanceIfEnough(int userId, double amount);
}
