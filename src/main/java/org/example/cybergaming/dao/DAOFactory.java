package org.example.cybergaming.dao;

import org.example.cybergaming.dao.interfaces.*;
import org.example.cybergaming.dao.impl.*;

public class DAOFactory {
    //Lấy UserDAO implementation
    public static UserDAO getUserDAO() {
        return new UserDAOImpl();
    }
    //Lấy PCDAO implementation
    public static PCDAO getPCDAO() {
        return new PCDAOImpl();
    }
    //Lấy FoodDAO implementation
    public static FoodDAO getFoodDAO() {
        return new FoodDAOImpl();
    }
    // Lấy BookingDAO implementation
    public static BookingDAO getBookingDAO() {
        return new BookingDAOImpl();
    }
    // Lấy OrderDAO implementation
    public static OrderDAO getOrderDAO() {
        return new OrderDAOImpl();
    }
    //Lấy UserDAO với custom implementation
    public static UserDAO getUserDAO(UserDAO implementation) {
        return implementation;
    }
    //Lấy PCDAO với custom implementation
    public static PCDAO getPCDAO(PCDAO implementation) {
        return implementation;
    }
    // Lấy FoodDAO với custom implementation
    public static FoodDAO getFoodDAO(FoodDAO implementation) {
        return implementation;
    }
    // Lấy BookingDAO với custom implementation
    public static BookingDAO getBookingDAO(BookingDAO implementation) {
        return implementation;
    }
    // Lấy OrderDAO với custom implementation
    public static OrderDAO getOrderDAO(OrderDAO implementation) {
        return implementation;
    }
}
