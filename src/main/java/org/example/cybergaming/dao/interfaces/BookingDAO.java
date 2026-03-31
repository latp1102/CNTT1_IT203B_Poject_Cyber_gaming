package org.example.cybergaming.dao.interfaces;

import org.example.cybergaming.model.Booking;

import java.time.LocalDateTime;
import java.util.List;

// Booking Data Access Object Interface
// Định nghĩa các phương thức thao tác với dữ liệu Booking
public interface BookingDAO {
    //Tạo booking mới
    int createBooking(Booking booking);
    //Lấy tất cả bookings
    List<Booking> getAllBookings();
    //Lấy bookings theo trạng thái
    List<Booking> getBookingsByStatus(String status);
    //Cập nhật trạng thái booking
    void updateStatus(int bookingId, String status);
    //Cập nhật trạng thái booking nếu trạng thái hiện tại hợp lệ
    boolean updateStatusIfCurrent(int bookingId, String currentStatus, String newStatus);
    //Tìm booking theo ID
    Booking findById(int id);
    // Lấy bookings theo user ID
    List<Booking> getBookingsByUserId(int userId);
    //Xóa booking theo ID
    boolean deleteBooking(int id);
    //Kiểm tra máy đã có booking trùng khung giờ ko
    boolean hasTimeConflict(int pcId, LocalDateTime start, LocalDateTime end);
}
