package dao;

import model.DonNhap;
import model.NhaCungCap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Collections;

public class NhapHangDAOTest {
    NhapHangDAO dao = new NhapHangDAO();

    @Test
    void lay_don_nhap_hang_voi_id_khong_ton_tai() {
        DonNhap don = dao.layDonNhapHang(99);
        Assertions.assertNull(don);
    }

    @Test
    void lay_don_nhap_hang_voi_id_ton_tai() {
        DonNhap don = dao.layDonNhapHang(44);
        NhaCungCap ncc = NhaCungCap.builder().id(1L)
                .name("WorldWide Engineering Co.")
                .code("FC151243").email("Keven.Darling@nowhere.com")
                .phone("0523293978").address("Quảng Trị")
                .build();
        DonNhap.Product pro = DonNhap.Product.builder()
                .code("BH1232415")
                .name("Bánh kem")
                .quantity(20)
                .price(120L)
                .build();
        DonNhap act = DonNhap.builder()
                .code("PH12341232")
                .hasReceived(false)
                .hasConfirmed(false)
                .hasPaid(false)
                .createdTime(new Timestamp(1651548228000L))
                .products(Collections.singletonList(pro))
                .provider(ncc)
                .id(44)
                .build();
        Assertions.assertEquals(don, act);
    }

    @Test
    void tao_ma_nhap_hang() {
        String ma = dao.taoMaNhapHang();
        Connection con = DAO.con;
        String sql = "select * from donnhaphang where tenDon = ?";
        try {
            PreparedStatement pr = con.prepareStatement(sql);
            pr.setString(1, ma);
            ResultSet rs = pr.executeQuery();
            Assertions.assertFalse(rs.next());
        } catch (Exception ignored) {
            Assertions.fail();
        }
    }

    @Test
    void tao_don_nhap_hang() {

    }
}
