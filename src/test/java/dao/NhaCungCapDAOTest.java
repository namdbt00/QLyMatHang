package dao;

import model.NhaCungCap;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NhaCungCapDAOTest {
    NhaCungCapDAO dao = new NhaCungCapDAO();

    @Test
    void addNhaCungCap() {
        NhaCungCap nhaCungCap = new NhaCungCap(null, "Abc", "111", "a@gmail.com", "0123456789", "HN");

        dao.addNhaCungCap(nhaCungCap);
        assertNotNull(nhaCungCap);

    }

    @Test
    void lay_nha_cung_cap_voi_id_khong_ton_tai() {
        NhaCungCap ncc = dao.layNhaCungCap(99);
        assertNull(ncc);
    }

    @Test
    void lay_nha_cung_cap_voi_id_ton_tai() {
        NhaCungCap ncc = dao.layNhaCungCap(1);
        NhaCungCap n = NhaCungCap.builder()
                .id(1L).name("WorldWide Engineering Co.")
                .code("FC151243")
                .email("Keven.Darling@nowhere.com")
                .phone("0523293978")
                .address("Quảng Trị")
                .build();
        assertEquals(ncc, n);
    }

    @Test
    void tim_nha_cung_cap_theo_ten() {
        // key khong co ket qua nao khop
        String keyword = "xxxxxxxxxx";
        List<NhaCungCap> list = dao.search(keyword);
        assertNotNull(list);
        assertEquals(0, list.size());

        // key co ket qua khop
        keyword = "ba";
        list = dao.search(keyword);
        assertNotNull(list);
        assertEquals(2, list.size());
        for (NhaCungCap ncc : list) {
            assertTrue(ncc.getName().toLowerCase().contains(keyword));
        }
    }
}