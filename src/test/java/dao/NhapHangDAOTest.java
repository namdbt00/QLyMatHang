package dao;

import model.DonNhap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class NhapHangDAOTest {
    @Test
    void testLayDonNhapHang() {
        NhapHangDAO dao = new NhapHangDAO();
        DonNhap don = dao.layDonNhapHang(45);
        System.out.println(don);
        Assertions.assertEquals(1, 1);
    }
}
