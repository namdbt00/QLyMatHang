package dao;

import model.Category;
import model.MatHang;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO extends DAO{
    public CategoryDAO() {
        super();
    }

    public List<Category> getListCategory() {
        List<Category> arrayList = new ArrayList<>();
        String sql = "call getListCategories()";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                Category ct = new Category();
                ct.setCategoryId(rs.getInt("categoryID"));
                ct.setName(rs.getString("name"));
                ct.setCreatedDate(rs.getDate("createdDate"));
                arrayList.add(ct);
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        return arrayList;
    }
}
