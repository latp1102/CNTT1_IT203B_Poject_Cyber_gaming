package org.example.cybergaming.dao.interfaces;

import org.example.cybergaming.model.PC;
import java.util.List;

public interface PCDAO {
    //Thêm PC mới
    boolean addPC(PC pc);
    //Lấy danh sách PC available theo thời gian
    List<PC> getAvailablePCs(int categoryId, String start, String end);
    // Cập nhật trạng thái PC
    boolean updatePCStatus(int pc_id, String status);
    // Lấy tất cả PCs
    List<PC> getAllPCs();
    // Tìm PC theo ID
    PC findById(int id);
    // Cập nhật thông tin PC
    boolean updatePC(PC pc);
    // Xóa PC theo ID
    boolean deletePC(int id);
}
