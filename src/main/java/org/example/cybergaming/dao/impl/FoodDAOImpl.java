package org.example.cybergaming.dao.impl;

import org.example.cybergaming.dao.interfaces.FoodDAO;
import org.example.cybergaming.model.Food;
import org.example.cybergaming.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class FoodDAOImpl implements FoodDAO {
    @Override
    public boolean addFood(Food food) {
        String sql = "insert into foods(name,description,price,stock) values(?,?,?,?)";
        try(Connection connection = DBConnection.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setString(1, food.getName());
            ps.setString(2, food.getDescription());
            ps.setDouble(3, food.getPrice());
            ps.setInt(4, food.getStock());
            return ps.executeUpdate() > 0;
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
    
    @Override
    public List<Food> getAllFoods() {
        List<Food> list = new ArrayList<>();
        String sql = "select * from foods";
        try(Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()){
            while(rs.next()){
                Food food = new Food();
                food.setId(rs.getInt("id"));
                food.setName(rs.getString("name"));
                food.setDescription(rs.getString("description"));
                food.setPrice(rs.getDouble("price"));
                food.setStock(rs.getInt("stock"));
                list.add(food);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return list;
    }
    
    @Override
    public boolean updateFood(Food food) {
        String sql = "update foods set name=?,description=?,price=?,stock=? where id=?";
        try(Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, food.getName());
            ps.setString(2, food.getDescription());
            ps.setDouble(3, food.getPrice());
            ps.setInt(4, food.getStock());
            ps.setInt(5, food.getId());
            return ps.executeUpdate() > 0;
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
    
    @Override
    public boolean deleteFood(int id) {
        String sql = "delete from foods where id=?";
        try(Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
    
    @Override
    public Food findById(int id) {
        String sql = "select * from foods where id=?";
        try(Connection connection = DBConnection.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                Food food = new Food();
                food.setId(rs.getInt("id"));
                food.setName(rs.getString("name"));
                food.setPrice(rs.getDouble("price"));
                food.setStock(rs.getInt("stock"));
                return food;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean decreaseStockIfEnough(int foodId, int quantity) {
        String sql = "UPDATE foods SET stock = stock - ? WHERE id = ? AND stock >= ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, quantity);
            ps.setInt(2, foodId);
            ps.setInt(3, quantity);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
