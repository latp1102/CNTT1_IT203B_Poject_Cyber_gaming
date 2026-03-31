package org.example.cybergaming.dao.interfaces;

import org.example.cybergaming.model.Food;
import java.util.List;

public interface FoodDAO {
    // thêm món ăn món
    boolean addFood(Food food);
    // lấy danh sách món ăn
    List<Food> getAllFoods();
    // cập nhật food
    boolean updateFood(Food food);
    // xóa món ăn theo id
    boolean deleteFood(int id);
    // tìm món theo id
    Food findById(int id);
    //Trừ tồn kho nếu còn đủ số lượng
    boolean decreaseStockIfEnough(int foodId, int quantity);
}
