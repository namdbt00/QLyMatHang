package dao;

import model.DonNhap;
import model.DonNhapHang;
import model.NhaCungCap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class NhapHangDAOTest {

    NhapHangDAO dao = new NhapHangDAO();

    @Test
    void getListDonNhapHang() {

        NhapHangDAO nhapHangDAO = new NhapHangDAO();

        //Search
        // Test case1
        String keyword = "xxxxxxxxxx";
        ArrayList<DonNhapHang> lisDonNhapHang = nhapHangDAO.getListDonNhapHang("search", keyword);
        assertNotNull(lisDonNhapHang);
        assertEquals(0, lisDonNhapHang.size());

        // Test case2
        keyword = "PH0169277";
        lisDonNhapHang = nhapHangDAO.getListDonNhapHang("search", keyword);
        assertNotNull(lisDonNhapHang);
        assertEquals(1, lisDonNhapHang.size());

        // Test case3
        keyword = "Home Foods Inc.";
        lisDonNhapHang = nhapHangDAO.getListDonNhapHang("search", keyword);
        assertNotNull(lisDonNhapHang);
        assertEquals(1, lisDonNhapHang.size());

        // Test case4
        keyword = "27";
        lisDonNhapHang = nhapHangDAO.getListDonNhapHang("search", keyword);
        assertNotNull(lisDonNhapHang);
        assertEquals(12, lisDonNhapHang.size());


        //Search-filter
        // Test case1
        keyword = "abcdef";
        lisDonNhapHang = nhapHangDAO.getListDonNhapHang("search-filter", keyword);
        assertNotNull(lisDonNhapHang);
        assertEquals(0, lisDonNhapHang.size());

        // Test case2
        keyword = "PH0169277";
        lisDonNhapHang = nhapHangDAO.getListDonNhapHang("search-filter", keyword);
        assertNotNull(lisDonNhapHang);
        assertEquals(1, lisDonNhapHang.size());

        // Test case3
        keyword = "Home Foods Inc.";
        lisDonNhapHang = nhapHangDAO.getListDonNhapHang("search-filter", keyword);
        assertNotNull(lisDonNhapHang);
        assertEquals(0, lisDonNhapHang.size());

        // Test case4
        keyword = "27";
        lisDonNhapHang = nhapHangDAO.getListDonNhapHang("search-filter", keyword);
        assertNotNull(lisDonNhapHang);
        assertEquals(2, lisDonNhapHang.size());

        //filter
        // Test case1
        lisDonNhapHang = nhapHangDAO.getListDonNhapHang("filter", "");
        assertNotNull(lisDonNhapHang);
        for (DonNhapHang donNhapHang : lisDonNhapHang) {
            assertEquals(0, donNhapHang.getIsPayment());
        }

        //default
        // Test case1
        lisDonNhapHang = nhapHangDAO.getListDonNhapHang("default", "");
        assertNotNull(lisDonNhapHang);
        assertEquals(30, lisDonNhapHang.size());
    }

    @Test
    void lay_don_nhap_hang_voi_id_khong_ton_tai() {
        DonNhap don = dao.layDonNhapHang(99);
        Assertions.assertNull(don);
    }

    @Test
    void lay_don_nhap_hang_voi_id_ton_tai() {
        DonNhap don = dao.layDonNhapHang(38);
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
    void tao_don_nhap_hang() throws SQLException {
        DAO.con.setAutoCommit(false);
        String ma = dao.taoMaNhapHang();
        NhaCungCap ncc = NhaCungCap.builder().id(1L)
                .name("WorldWide Engineering Co.")
                .code("FC151243").email("Keven.Darling@nowhere.com")
                .phone("0523293978").address("Quảng Trị")
                .build();
        DonNhap.Product pro = DonNhap.Product.builder()
                .code("BH1232415").name("Bánh kem")
                .quantity(20).price(120L).id(111)
                .build();
        DonNhap don = DonNhap.builder()
                .providerId(Math.toIntExact(ncc.getId()))
                .code(ma).hasReceived(false).hasConfirmed(false)
                .hasPaid(false).products(Collections.singletonList(pro))
                .provider(ncc).id(44).build();

        Integer id = dao.taoDonNhapHang(don);
        DonNhap created = dao.layDonNhapHang(id);

        don.setId(id);
        don.setProviderId(null);
        don.getProducts().get(0).setId(null);
        don.setCreatedTime(created.getCreatedTime());

        assertEquals(don, created);
        DAO.con.rollback();
    }
}