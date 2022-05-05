package dao;

import model.DonNhapHang;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class NhapHangDAOTest {

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
}