package org.example.cybergaming.service;

import org.example.cybergaming.dao.DAOFactory;
import org.example.cybergaming.dao.interfaces.UserDAO;
import org.example.cybergaming.model.User;
import org.example.cybergaming.util.HashUtil;

public class AuthService {
    private UserDAO userDAO = DAOFactory.getUserDAO();
    public boolean register(String username, String password, String fullName, String phone, String role){
        if(username == null || username.trim().isEmpty()){
            System.out.println("Tên người dùng không để trống");
            return false;
        }
        if(password == null || password.isEmpty()){
            System.out.println("Mật khẩu không để trống");
            return false;
        }
        if(password.length() < 6){
            System.out.println("Mật khẩu phải có ít nhất 6 kí tự");
            return false;
        }
        if(phone == null || phone.trim().isEmpty()){
            System.out.println("Số điện thoại không để trống");
            return false;
        }
        if (fullName == null || fullName.trim().isEmpty()){
            System.out.println("Họ và tên không để trống");
            return false;
        }
        // Validation số điện thoại Việt Nam (bắt đầu bằng 0, dài 10-11 số)
        if(!phone.matches("^(0)[0-9]{9,10}$")){
            System.out.println("Số điện thoại không đúng định dạng (phải bắt đầu bằng 0 và có 10-11 số)");
            return false;
        }
        String hash = HashUtil.hashPassword(password);
        User user = new User(0.0, fullName, 0, hash, phone, role, username);
        return userDAO.registerUser(user);
    }
    public User login(String username, String password){

        User user = userDAO.login(username);

        if(user == null){
            return null;
        }
        String hash = HashUtil.hashPassword(password);
        if(hash.equals(user.getPassword())){
            return user;
        }
        return null;
    }

}
