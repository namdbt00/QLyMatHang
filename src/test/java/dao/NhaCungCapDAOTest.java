package dao;

import model.NhaCungCap;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class NhaCungCapDAOTest {

    @Test
    void addNhaCungCap() {
        NhaCungCapDAO nhaCungCapDAO = new NhaCungCapDAO();
        NhaCungCap nhaCungCap = new NhaCungCap(null, "Abc", "111", "a@gmail.com", "0123456789", "HN");

        nhaCungCapDAO.addNhaCungCap(nhaCungCap);
        assertNotNull(nhaCungCap);

    }
}