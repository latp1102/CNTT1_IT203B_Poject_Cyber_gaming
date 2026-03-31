package org.example.cybergaming.dao.impl;

import org.example.cybergaming.dao.interfaces.PCDAO;
import org.example.cybergaming.model.PC;
import org.example.cybergaming.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * PCDAO Implementation
 * Triển khai các phương thức thao tác với dữ liệu PC trong MySQL
 */
public class PCDAOImpl implements PCDAO {
    
    @Override
    public boolean addPC(PC pc) {
        String sql = "insert into pcs(pc_number,category_id,configuration,price_per_hour,status) values(?,?,?,?,?)";
        try(Connection connection = DBConnection.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, pc.getPcNumber());
            ps.setInt(2, pc.getCategoryId());
            ps.setString(3, pc.getConfiguration());
            ps.setDouble(4, pc.getPricePerHour());
            ps.setString(5, "available");
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    // trùng lịch not
    @Override
    public List<PC> getAvailablePCs(int categoryId, String start, String end) {
        List<PC> list = new ArrayList<>();
        String sql = """
                select * from pcs p
                where p.category_id = ?
            and p.status = 'available'
                and p.id not in (
                    select pc_id from bookings
                    where status in ('pending','confirmed','serving')
                    and not (
                        ? <= start_time
                        or
                        ? >= end_time
                    )
                )
                """;
        try(Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, categoryId);
            ps.setString(2, end);
            ps.setString(3, start);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                PC pc = new PC();
                pc.setId(rs.getInt("id"));
                pc.setPcNumber(rs.getString("pc_number"));
                pc.setCategoryId(rs.getInt("category_id"));
                pc.setConfiguration(rs.getString("configuration"));
                pc.setPricePerHour(rs.getDouble("price_per_hour"));
                pc.setStatus(rs.getString("status"));
                list.add(pc);
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        return list;
    }
    
    @Override
    public boolean updatePCStatus(int pc_id, String status) {
        String sql = "update pcs set status=? where id=?";
        try(Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setDouble(2, pc_id);
            return ps.executeUpdate() > 0;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    
    @Override
    public List<PC> getAllPCs() {
        List<PC> list = new ArrayList<>();
        String sql = "select * from pcs";
        try(Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {
            while (rs.next()){
                PC pc = new PC();
                pc.setId(rs.getInt("id"));
                pc.setPcNumber(rs.getString("pc_number"));
                pc.setCategoryId(rs.getInt("category_id"));
                pc.setConfiguration(rs.getString("configuration"));
                pc.setPricePerHour(rs.getDouble("price_per_hour"));
                pc.setStatus(rs.getString("status"));
                list.add(pc);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }
    
    @Override
    public PC findById(int id) {
        String sql = "select * from pcs where id=?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                PC pc = new PC();
                pc.setId(rs.getInt("id"));
                pc.setPcNumber(rs.getString("pc_number"));
                pc.setCategoryId(rs.getInt("category_id"));
                pc.setConfiguration(rs.getString("configuration"));
                pc.setPricePerHour(rs.getDouble("price_per_hour"));
                pc.setStatus(rs.getString("status"));
                return pc;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    @Override
    public boolean updatePC(PC pc) {
        String sql = "update pcs set pc_number=?, category_id=?, configuration=?, price_per_hour=?, status=? where id=?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, pc.getPcNumber());
            ps.setInt(2, pc.getCategoryId());
            ps.setString(3, pc.getConfiguration());
            ps.setDouble(4, pc.getPricePerHour());
            ps.setString(5, pc.getStatus());
            ps.setInt(6, pc.getId());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    @Override
    public boolean deletePC(int id) {
        String sql = "delete from pcs where id=?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
