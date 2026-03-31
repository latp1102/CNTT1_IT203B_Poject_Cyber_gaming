package org.example.cybergaming.dao.impl;

import org.example.cybergaming.dao.interfaces.BookingDAO;
import org.example.cybergaming.model.Booking;
import org.example.cybergaming.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


// BookingDAO Implementation
// Triển khai các phương thức thao tác với dữ liệu Booking trong MySQL
public class BookingDAOImpl implements BookingDAO {
    @Override
    public int createBooking(Booking booking) {
        String sql = """
                INSERT INTO bookings (user_id, pc_id, start_time, end_time, status, total_cost)
                SELECT ?, ?, ?, ?, ?, ?
                WHERE NOT EXISTS (
                    SELECT 1 FROM bookings
                    WHERE pc_id = ?
                    AND status IN ('pending', 'confirmed', 'serving')
                    AND NOT (? <= start_time OR ? >= end_time)
                )
                """;
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, booking.getUserId());
            ps.setInt(2, booking.getPcId());
            ps.setTimestamp(3, Timestamp.valueOf(booking.getStartTime()));
            ps.setTimestamp(4, Timestamp.valueOf(booking.getEndTime()));
            ps.setString(5, "pending");
            ps.setDouble(6, 0);
            ps.setInt(7, booking.getPcId());
            ps.setTimestamp(8, Timestamp.valueOf(booking.getEndTime()));
            ps.setTimestamp(9, Timestamp.valueOf(booking.getStartTime()));

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                return -1;
            }
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public List<Booking> getAllBookings() {
        List<Booking> list = new ArrayList<>();
        String sql = "SELECT * FROM bookings";
        try (Connection connection = DBConnection.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Booking b = new Booking();
                b.setId(rs.getInt("id"));
                b.setUserId(rs.getInt("user_id"));
                b.setPcId(rs.getInt("pc_id"));
                b.setStartTime(rs.getTimestamp("start_time").toLocalDateTime());
                b.setEndTime(rs.getTimestamp("end_time").toLocalDateTime());
                b.setStatus(rs.getString("status"));
                b.setTotalCost(rs.getDouble("total_cost"));
                list.add(b);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Booking> getBookingsByStatus(String status) {
        List<Booking> list = new ArrayList<>();
        String sql = "SELECT * FROM bookings WHERE status=? ORDER BY start_time ASC";
        try (Connection connection = DBConnection.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, status);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Booking b = new Booking();
                b.setId(rs.getInt("id"));
                b.setUserId(rs.getInt("user_id"));
                b.setPcId(rs.getInt("pc_id"));
                b.setStartTime(rs.getTimestamp("start_time").toLocalDateTime());
                b.setEndTime(rs.getTimestamp("end_time").toLocalDateTime());
                b.setStatus(rs.getString("status"));
                b.setTotalCost(rs.getDouble("total_cost"));
                list.add(b);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    @Override
    public void updateStatus(int bookingId, String status) {
        String sql = "UPDATE bookings SET status=? WHERE id=?";
        try (Connection connection = DBConnection.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, bookingId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public boolean updateStatusIfCurrent(int bookingId, String currentStatus, String newStatus) {
        String sql = "UPDATE bookings SET status=? WHERE id=? AND status=?";
        try (Connection connection = DBConnection.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, newStatus);
            ps.setInt(2, bookingId);
            ps.setString(3, currentStatus);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Booking findById(int id) {
        String sql = "SELECT * FROM bookings WHERE id=?";
        try (Connection connection = DBConnection.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Booking b = new Booking();
                b.setId(rs.getInt("id"));
                b.setUserId(rs.getInt("user_id"));
                b.setPcId(rs.getInt("pc_id"));
                b.setStartTime(rs.getTimestamp("start_time").toLocalDateTime());
                b.setEndTime(rs.getTimestamp("end_time").toLocalDateTime());
                b.setStatus(rs.getString("status"));
                b.setTotalCost(rs.getDouble("total_cost"));
                return b;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Booking> getBookingsByUserId(int userId) {
        List<Booking> list = new ArrayList<>();
        String sql = "SELECT * FROM bookings WHERE user_id=?";
        try (Connection connection = DBConnection.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Booking b = new Booking();
                b.setId(rs.getInt("id"));
                b.setUserId(rs.getInt("user_id"));
                b.setPcId(rs.getInt("pc_id"));
                b.setStartTime(rs.getTimestamp("start_time").toLocalDateTime());
                b.setEndTime(rs.getTimestamp("end_time").toLocalDateTime());
                b.setStatus(rs.getString("status"));
                b.setTotalCost(rs.getDouble("total_cost"));
                list.add(b);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean deleteBooking(int id) {
        String sql = "DELETE FROM bookings WHERE id=?";
        try (Connection connection = DBConnection.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean hasTimeConflict(int pcId, java.time.LocalDateTime start, java.time.LocalDateTime end) {
        String sql = """
                SELECT COUNT(*) FROM bookings
                WHERE pc_id = ?
                AND status IN ('pending', 'confirmed', 'serving')
                AND NOT (? <= start_time OR ? >= end_time)
                """;
        try (Connection connection = DBConnection.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, pcId);
            ps.setTimestamp(2, Timestamp.valueOf(end));
            ps.setTimestamp(3, Timestamp.valueOf(start));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
