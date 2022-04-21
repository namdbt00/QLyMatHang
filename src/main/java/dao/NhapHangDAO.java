package dao;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import model.DonNhap;
import model.DonNhapHang;
import model.NhaCungCap;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class NhapHangDAO extends DAO {

    public ArrayList<DonNhapHang> getListDonNhapHang(String action, String keyword) {

        ArrayList<DonNhapHang> lisDonNhapHang = new ArrayList<>();
        CallableStatement cs = null;
        String sql;

        try {
            switch (action) {
                case "filter":
                    sql = "{call filterDonNhapHang()}";
                    cs = con.prepareCall(sql);
                    break;
                case "search":
                    sql = "{call searchDonNhapHang(?)}";
                    cs = con.prepareCall(sql);
                    cs.setString(1, keyword);
                    break;
                case "search-filter":
                    sql = "{call searchDonNhapHangFilter(?)}";
                    cs = con.prepareCall(sql);
                    cs.setString(1, keyword);
                    break;
                case "default":
                    sql = "{call getListDonNhapHang()}";
                    cs = con.prepareCall(sql);
                    break;
            }

            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                DonNhapHang donNhapHang = new DonNhapHang();
                donNhapHang.setDonId(rs.getInt("donId"));
                donNhapHang.setTenDon(rs.getString("tenDon"));
                donNhapHang.setNhaCungCapID(rs.getInt("nhaCungCapID"));
                donNhapHang.setIsPayment(rs.getInt("isPayment"));
                donNhapHang.setPaymentTime(rs.getTimestamp("paymentTime"));
                donNhapHang.setIsImportToWarehouse(rs.getInt("isImportToWarehouse"));
                donNhapHang.setImportTime(rs.getTimestamp("importTime"));
                donNhapHang.setCreateDate(rs.getDate("createdDate"));
                donNhapHang.setConfirmDate(rs.getDate("confirmDate"));
                donNhapHang.setIsConfirm(rs.getInt("isConfirm"));
                donNhapHang.setNhaCungCap(rs.getString("nhaCungCap"));
                donNhapHang.setTotalPrice(rs.getString("totalPrice"));
                lisDonNhapHang.add(donNhapHang);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lisDonNhapHang;
    }

    public String getNewCode() {
        String sql = "call getMaDonMoiNhat()";
        String code = "PH000001";
        if (con == null) return code;
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                code = rs.getString(1);
                code = "PH" + (Integer.parseInt(code.substring(2)) + 1);
            }
            return code;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return code;
    }

    public Integer createNew(DonNhap data) {
        String sql = "call taoDonMoi(?,?,?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, data.getCode());
            ps.setInt(2, data.getProviderId());
            ps.setString(3, getJson(data.getProducts()));
            ps.executeUpdate();
            sql = "select donID from donnhaphang where tenDon = ? limit 1";
            ps = con.prepareStatement(sql);
            ps.setString(1, data.getCode());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            } else return -1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    private String getJson(List<DonNhap.Product> products) {
        List<DonMatHang> donMatHangs = new ArrayList<>();
        for (DonNhap.Product product : products) {
            donMatHangs.add(DonMatHang.builder()
                    .matHangID(product.getId())
                    .quantity(product.getQuantity())
                    .importPrice(product.getPrice())
                    .build()
            );
        }
        return new Gson().toJson(donMatHangs);
    }

    public DonNhap layDonNhapHang(Integer id) {
        String sql = "call getBill(?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Integer providerId = rs.getInt("nhaCungCapID");
                DonNhap don = DonNhap.builder()
                        .id(rs.getInt("donID"))
                        .code(rs.getString("tenDon"))
                        .hasPaid(rs.getInt("isPayment") == 1)
                        .hasReceived(rs.getInt("isImportToWarehouse") == 1)
                        .hasConfirmed(rs.getInt("isConfirm") == 1)
                        .importTime(rs.getDate("importTime"))
                        .paymentTime(rs.getDate("paymentTime"))
                        .confirmTime(rs.getDate("confirmDate"))
                        .createdTime(rs.getTime("createdDate"))
                        .build();
                NhaCungCapDAO dao = new NhaCungCapDAO();
                NhaCungCap ncc = dao.layNhaCungCap(providerId);
                don.setProvider(ncc);

                sql = "SELECT mathang.matHangID, mathang.matHangCode, quantity, importPrice, name from mathang " +
                        "left join don_mathang on mathang.matHangID = don_mathang.matHangID where donId = ?";
                ps = con.prepareStatement(sql);
                ps.setInt(1, id);
                rs = ps.executeQuery();
                ArrayList<DonNhap.Product> list = new ArrayList<>();
                while (rs.next()) {
                    DonNhap.Product pro = DonNhap.Product.builder()
                            .id(rs.getInt("matHangID"))
                            .name(rs.getString("name"))
                            .code(rs.getString("matHangCode"))
                            .price(rs.getLong("importPrice"))
                            .quantity(rs.getInt("quantity"))
                            .build();
                    list.add(pro);
                }
                don.setProducts(list);
                return don;
            } else return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean xacNhanDonNhapKho(Integer id) {
        String sql = "UPDATE donnhaphang SET confirmDate = now(), isConfirm = 1 WHERE donID = ?;";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            return ps.executeUpdate() != 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean xacNhanDaNhapHang(Integer id) {
        String sql = "UPDATE donnhaphang SET isImportToWarehouse = 1, importTime = now() WHERE donID = ?;";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            return ps.executeUpdate() != 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean xacNhanDaThanhToan(Integer id) {
        String sql = "UPDATE donnhaphang SET isPayment = 1, paymentTime = now() WHERE donID = ?;";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            return ps.executeUpdate() != 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class DonMatHang {
        private Integer matHangID;
        private Integer quantity;
        private Long importPrice;
    }
}
