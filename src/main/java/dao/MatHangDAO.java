package dao;

import model.Category;
import model.MatHang;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MatHangDAO extends DAO {

    public MatHangDAO() {
        super();
    }

    List<Category> categories = null;

    public MatHang getMatHangById(int matHangId) {
//        m.matHangID,m.matHangCode,m.name,m.image,
//m.retailPrice,m.wholesalePrice,m.description,
//m.categoryID,m.createdDate,m.calculateUnit,
//m.unit,m.weight,m.attribute,mk.quantity
        MatHang result = new MatHang();
        String sql = "call getMatHangById(?)";
        try {

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, matHangId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                result.setId(Long.parseLong(matHangId + ""));
                result.setCode(rs.getString("matHangCode"));
                result.setName(rs.getString("name"));
                result.setImage(rs.getBlob("image").getBinaryStream());
                result.setRetailPrice(rs.getDouble("retailPrice"));
                result.setWholesalePrice(rs.getDouble("wholesalePrice"));
                result.setDescription(rs.getString("description"));
                int categoryId = rs.getInt("categoryID");
                Category category = new Category();
                CategoryDAO categoryDAO = new CategoryDAO();
                categories = categoryDAO.getListCategory();

                for (int i = 0; i < categories.size(); i++) {
                    if (categories.get(i).getCategoryId() == categoryId) {
                        category = categories.get(i);
                    }
                }
                result.setCategory(category);
                result.setCreatedDate(rs.getDate("createdDate"));
                result.setCalculateUnit(rs.getString("calculateUnit"));
                result.setUnit(rs.getInt("unit"));
                result.setWeight(rs.getFloat("weight"));
                result.setAttribute(rs.getString("attribute"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean saveMatHang(MatHang matHang) {
        //matHangID, matHangCode , name,
        // image, retailPrice,
        // wholesalePrice, description,
        // categoryID, attribute, calculateUnit, unit, weight
        boolean check = true;
        String sql = "call addMatHang(?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, 0);
            ps.setString(2, matHang.getCode());
            ps.setString(3, matHang.getName());
            ps.setBlob(4, matHang.getImage());
            ps.setDouble(5, matHang.getRetailPrice());
            ps.setDouble(6, matHang.getWholesalePrice());
            ps.setString(7, matHang.getDescription());
            ps.setInt(8, matHang.getCategory().getCategoryId());
            ps.setString(9, matHang.getAttribute());
            ps.setString(10, matHang.getCalculateUnit());
            ps.setInt(11, matHang.getUnit());
            ps.setFloat(12, matHang.getWeight());
            ps.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
            check = false;
        }
        return check;
    }

    public boolean updateMatHangWithoutImage(MatHang matHang) {
        //matHangID, matHangCode , name,
        //retailPrice,
        // wholesalePrice, description,
        // categoryID, attribute, calculateUnit, unit, weight
        boolean check = true;
        String sql = "call updateMatHangWithoutImage(?,?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, Integer.parseInt(matHang.getId() + ""));
            ps.setString(2, matHang.getCode());
            ps.setString(3, matHang.getName());
//            ps.setBlob(4, matHang.getImage());
            ps.setDouble(4, matHang.getRetailPrice());
            ps.setDouble(5, matHang.getWholesalePrice());
            ps.setString(6, matHang.getDescription());
            ps.setInt(7, matHang.getCategory().getCategoryId());
            ps.setString(8, matHang.getAttribute());
            ps.setString(9, matHang.getCalculateUnit());
            ps.setInt(10, matHang.getUnit());
            ps.setFloat(11, matHang.getWeight());
            ps.executeQuery();
            System.out.println("Done update");
        } catch (Exception e) {
            e.printStackTrace();
            check = false;
        }
        return check;
    }

    public boolean updateMatHangWithImage(MatHang matHang) {
        //matHangID, matHangCode , name,
        // image, retailPrice,
        // wholesalePrice, description,
        // categoryID, attribute, calculateUnit, unit, weight
        boolean check = true;
        String sql = "call updateMatHangWithImage(?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, Integer.parseInt(matHang.getId() + ""));
            ps.setString(2, matHang.getCode());
            ps.setString(3, matHang.getName());
            ps.setBlob(4, matHang.getImage());
            ps.setDouble(5, matHang.getRetailPrice());
            ps.setDouble(6, matHang.getWholesalePrice());
            ps.setString(7, matHang.getDescription());
            ps.setInt(8, matHang.getCategory().getCategoryId());
            ps.setString(9, matHang.getAttribute());
            ps.setString(10, matHang.getCalculateUnit());
            ps.setInt(11, matHang.getUnit());
            ps.setFloat(12, matHang.getWeight());
            ps.executeQuery();
            System.out.println("Done update");
        } catch (Exception e) {
            e.printStackTrace();
            check = false;
        }
        return check;
    }

    public List<MatHang> getListMatHang() {
        List<MatHang> arrayList = new ArrayList<>();
        String sql = "call getListMatHang(?)";
        MatHang mh = new MatHang();
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, 1);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                mh.setName(rs.getString("name"));
                arrayList.add(mh);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return arrayList;
    }

    public List<MatHang> search(String key) {
        List<MatHang> list = new ArrayList<>();
        String sql = "call getListMatHangAll(?)";
        if (con == null) return list;
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, key == null ? "" : key);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                MatHang mh = MatHang.builder().id(rs.getLong(1))
                        .code(rs.getString(2)).name(rs.getString(3)).build();
                list.add(mh);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<MatHang> getall() {
        List<MatHang> list = new ArrayList<>();
        String sql = "call getListMatHang()";
        try {
            PreparedStatement st = con.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                MatHang m = new MatHang();
                m.setId(rs.getLong("matHangID"));
                m.setName(rs.getString("name"));
                m.setCode(rs.getString("matHangCode"));
                m.setRetailPrice(rs.getDouble("retailPrice"));
                m.setWholesalePrice(rs.getDouble("wholesalePrice"));
                m.setQuantity(rs.getInt("quantity"));
                list.add(m);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }

    public void deleteMH(int id) {

        try {
            String sql = "call deleteMatHang(?)";
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, id);
            st.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<MatHang> searchByName(String txtSearch) {
        List<MatHang> list = new ArrayList<>();
        String sql = "call searchMatHang(?)";
        try {
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, "%" + txtSearch + "%");
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                MatHang m = new MatHang();
                m.setId(rs.getLong("matHangID"));
                m.setName(rs.getString("name"));
                m.setCode(rs.getString("matHangCode"));
                m.setRetailPrice(rs.getDouble("retailPrice"));
                m.setWholesalePrice(rs.getDouble("wholesalePrice"));
                m.setCreatedDate(rs.getDate("createdDate"));
                list.add(m);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


}
