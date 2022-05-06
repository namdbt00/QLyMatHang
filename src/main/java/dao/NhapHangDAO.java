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
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NhapHangDAO extends DAO {
    private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

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
                donNhapHang.setCreateDate(rs.getTimestamp("createdDate"));
                donNhapHang.setConfirmDate(rs.getTimestamp("confirmDate"));
                donNhapHang.setIsConfirm(rs.getInt("isConfirm"));
                donNhapHang.setNhaCungCap(rs.getString("nhaCungCap"));
                donNhapHang.setTotalPrice(rs.getLong("totalPrice"));
                lisDonNhapHang.add(donNhapHang);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lisDonNhapHang;
    }

    public String taoMaNhapHang() {
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

    public Integer taoDonNhapHang(DonNhap data) {
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
            String json;
            if (rs.next()) {
                Integer providerId = rs.getInt("nhaCungCapID");
                DonNhap don = DonNhap.builder()
                        .id(rs.getInt("donID"))
                        .code(rs.getString("tenDon"))
                        .hasPaid(rs.getInt("isPayment") == 1)
                        .hasReceived(rs.getInt("isImportToWarehouse") == 1)
                        .hasConfirmed(rs.getInt("isConfirm") == 1)
                        .importTime(rs.getTimestamp("importTime"))
                        .paymentTime(rs.getTimestamp("paymentTime"))
                        .confirmTime(rs.getTimestamp("confirmDate"))
                        .createdTime(rs.getTimestamp("createdDate"))
                        .build();
                json = rs.getString("listProducts");
                NhaCungCapDAO dao = new NhaCungCapDAO();
                NhaCungCap ncc = dao.layNhaCungCap(providerId);
                don.setProvider(ncc);
                DonNhap.Product[] list = new Gson().fromJson(json, DonNhap.Product[].class);
                don.setProducts(Arrays.asList(list));
                return don;
            } else return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean xacNhanDonNhapHang(Integer id, Long paymentTime, Long importTime) {
        String sql = "call updateDon(?,?,?,?,?,?,?)";
        try {
            Timestamp confirmT = new Timestamp(System.currentTimeMillis());
            Timestamp importT = importTime == -1 ? null : new Timestamp(importTime);
            Timestamp paymentT = paymentTime == -1 ? null : new Timestamp(paymentTime);
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setInt(2, paymentTime == -1 ? 0 : 1);
            ps.setInt(4, importTime == -1 ? 0 : 1);
            ps.setInt(7, 1);
            ps.setTimestamp(3, paymentT);
            ps.setTimestamp(5, importT);
            ps.setTimestamp(6, confirmT);
            return ps.executeUpdate() != 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean xacNhanDaNhapHang(Integer id, Long confirmTime, Long paymentTime) {
        String sql = "call updateDon(?,?,?,?,?,?,?)";
        try {
            Timestamp importT = new Timestamp(System.currentTimeMillis());
            Timestamp confirmT = confirmTime == -1 ? null : new Timestamp(confirmTime);
            Timestamp paymentT = paymentTime == -1 ? null : new Timestamp(paymentTime);
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setInt(2, paymentTime == -1 ? 0 : 1);
            ps.setInt(4, 1);
            ps.setInt(7, confirmTime == -1 ? 0 : 1);
            ps.setTimestamp(3, paymentT);
            ps.setTimestamp(5, importT);
            ps.setTimestamp(6, confirmT);
            return ps.executeUpdate() != 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean xacNhanDaThanhToan(Integer id, Long confirmTime, Long importTime) {
        String sql = "call updateDon(?,?,?,?,?,?,?)";
        try {
            Timestamp paymentT = new Timestamp(System.currentTimeMillis());
            Timestamp importT = importTime == -1 ? null : new Timestamp(importTime);
            Timestamp confirmT = confirmTime == -1 ? null : new Timestamp(confirmTime);
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setInt(2, 1);
            ps.setInt(4, importTime == -1 ? 0 : 1);
            ps.setInt(7, confirmTime == -1 ? 0 : 1);
            ps.setTimestamp(3, paymentT);
            ps.setTimestamp(5, importT);
            ps.setTimestamp(6, confirmT);
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
