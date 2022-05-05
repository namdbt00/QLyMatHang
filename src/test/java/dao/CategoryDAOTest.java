package dao;

import model.Category;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CategoryDAOTest {
    @Test
    void testGetListCategory() {
        CategoryDAO cdao = new CategoryDAO();
        List<Category> arrayList = cdao.getListCategory();
        assertNotNull(arrayList);
        assertEquals(4, arrayList.size());
    }
}