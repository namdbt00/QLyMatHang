package dao;

import model.NhaCungCap;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class NhaCungCapDAO extends DAO {
    public List<NhaCungCap> search(String key) {
        List<NhaCungCap> list = new ArrayList<>();
        String sql = "call getListNhaCungCap(?)";
        if (con == null) return list;
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, key == null ? "" : key);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                NhaCungCap ncc = NhaCungCap.builder().id(rs.getLong(1))
                        .name(rs.getString(2)).code(rs.getString(3)).email(rs.getString(4))
                        .phone(rs.getString(5)).address(rs.getString(6)).build();
                list.add(ncc);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    public boolean addNhaCungCap(NhaCungCap nhaCungCap){

        boolean result = true;
        String sql = "{call addNhaCungCap(?,?,?,?,?)}";

        try {
            CallableStatement cs = con.prepareCall(sql);
            cs.setString(1,nhaCungCap.getCode());
            cs.setString(2,nhaCungCap.getName());
            cs.setString(3,nhaCungCap.getEmail());
            cs.setString(4,nhaCungCap.getPhone());
            cs.setString(5,nhaCungCap.getAddress());

            cs.executeQuery();
        }catch (Exception e){
            result = false;
            e.printStackTrace();
        }
        return result;
    }

    public NhaCungCap layNhaCungCap(Integer providerId) {
        String sql = "select * from nhacungcap where nhanCungCapId = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, providerId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return NhaCungCap.builder().id(rs.getLong(1))
                        .name(rs.getString(2)).code(rs.getString(3)).email(rs.getString(4))
                        .phone(rs.getString(5)).address(rs.getString(6)).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
