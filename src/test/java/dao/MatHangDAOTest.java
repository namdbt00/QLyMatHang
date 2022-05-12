package dao;

import com.example.mathang.SuaMatHangServlet;
import com.example.mathang.ThemMatHangServlet;
import lombok.SneakyThrows;
import model.Category;
import model.MatHang;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MatHangDAOTest {
    //
    @Test
    void testGetMatHangById() {
        MatHangDAO daoMH = new MatHangDAO();
        MatHang matHang = daoMH.getMatHangById(111);
        String expected = "BH1232415";
        assertEquals(expected, matHang.getCode());
    }

    //
    @Test
    void testSaveMatHang() {
        ThemMatHangServlet tmhS = new ThemMatHangServlet();
        String code = "ABC";
        String name = "Ao phong";
        InputStream image = null;
        Double retailPrice = Double.parseDouble("255");
        Double wholesalePrice = Double.parseDouble("100");
        Integer unit = Integer.parseInt(1 + "");
        String calculateUnit = "Cai";
        Float weight = Float.parseFloat("15");
        String description = "Day la mat hang test";
        Integer categoryId = Integer.parseInt(1 + "");
        String attribute = "[{\"atbName\":\"Kích cỡ\",\"atbValue\":\"X\"},{\"atbName\":\"Kích cỡ \",\"atbValue\":\"Y\"}]-[{\"unitName\":\"Thùng\",\"unitValue\":30}]";
        CategoryDAO categoryDAO = new CategoryDAO();
        tmhS.categories = categoryDAO.getListCategory();
        Category category = tmhS.getCategoryById(categoryId);
        MatHang expectMatHang = new MatHang();
        expectMatHang.setCode(code);
        expectMatHang.setName(name);
        expectMatHang.setImage(image);
        expectMatHang.setRetailPrice(retailPrice);
        expectMatHang.setWholesalePrice(wholesalePrice);
        expectMatHang.setUnit(unit);
        expectMatHang.setCalculateUnit(calculateUnit);
        expectMatHang.setWeight(weight);
        expectMatHang.setDescription(description);
        expectMatHang.setCategory(category);
        expectMatHang.setAttribute(attribute);

        MatHang matHang = tmhS.buildMatHang(code, name, image, retailPrice,
                wholesalePrice, unit, calculateUnit,
                weight, description, category, attribute);
        MatHangDAO daoMH = new MatHangDAO();
        boolean check = daoMH.saveMatHang(matHang);
        boolean expected = true;
        assertEquals(expected, check);
        List<MatHang> listMH = daoMH.getall();
        assertEquals(matHang.getCode(), listMH.get(0).getCode());
        int idMH = Integer.parseInt(listMH.get(0).getId() + "");
        daoMH.deleteMH(idMH);
    }

    //
    @Test
    void testUpdateMatHangWithoutImage() {
        SuaMatHangServlet smhS = new SuaMatHangServlet();
        String code = "ABC";
        String name = "Ao phong";
        String initialString = "text";
        InputStream image = new ByteArrayInputStream(initialString.getBytes());
        Double retailPrice = Double.parseDouble("255");
        Double wholesalePrice = Double.parseDouble("100");
        Integer unit = Integer.parseInt(1 + "");
        String calculateUnit = "Cai";
        Float weight = Float.parseFloat("15");
        String description = "Day la mat hang test";
        Integer categoryId = Integer.parseInt(1 + "");
        String attribute = "[{\"atbName\":\"Kích cỡ\",\"atbValue\":\"X\"},{\"atbName\":\"Kích cỡ \",\"atbValue\":\"Y\"}]-[{\"unitName\":\"Thùng\",\"unitValue\":30}]";
        CategoryDAO categoryDAO = new CategoryDAO();
        smhS.categories = categoryDAO.getListCategory();
        Category category = smhS.getCategoryById(categoryId);
        MatHang expectMatHang = new MatHang();
        expectMatHang.setCode(code);
        expectMatHang.setName(name);
        expectMatHang.setRetailPrice(retailPrice);
        expectMatHang.setWholesalePrice(wholesalePrice);
        expectMatHang.setUnit(unit);
        expectMatHang.setCalculateUnit(calculateUnit);
        expectMatHang.setWeight(weight);
        expectMatHang.setDescription(description);
        expectMatHang.setCategory(category);
        expectMatHang.setAttribute(attribute);
        MatHangDAO mhDAO = new MatHangDAO();
        MatHang mahangBD = mhDAO.getMatHangById(111);
        MatHang matHang = smhS.buildMatHangWithoutImage(Long.parseLong(111 + ""), code, name, retailPrice,
                wholesalePrice, unit, calculateUnit,
                weight, description, category, attribute);
        boolean check = smhS.daoUpdateWithoutImage(matHang);
        boolean expected = true;
        assertEquals(expected, check);
        MatHang mathangUpdated = mhDAO.getMatHangById(111);
        boolean roleback = smhS.daoUpdateWithoutImage(mahangBD);
        assertEquals(matHang.getId(), mathangUpdated.getId());
        assertEquals(matHang.getCode(), mathangUpdated.getCode());
        assertEquals(matHang.getName(), mathangUpdated.getName());

        assertEquals(matHang.getRetailPrice(), mathangUpdated.getRetailPrice());
        assertEquals(matHang.getWholesalePrice(), mathangUpdated.getWholesalePrice());
        assertEquals(matHang.getUnit(), mathangUpdated.getUnit());
        assertEquals(matHang.getCalculateUnit(), mathangUpdated.getCalculateUnit());
        assertEquals(matHang.getWeight(), mathangUpdated.getWeight());
        assertEquals(matHang.getDescription(), mathangUpdated.getDescription());
        assertEquals(matHang.getCategory().getName(), mathangUpdated.getCategory().getName());
        assertEquals(matHang.getAttribute(), mathangUpdated.getAttribute());
    }

    //
    @SneakyThrows
    @Test
    void testUpdateMatHangWithImage() {
        SuaMatHangServlet smhS = new SuaMatHangServlet();
        String code = "ABC";
        String name = "Ao phong";
        String initialString = "text";
        InputStream image = new ByteArrayInputStream(initialString.getBytes());
        Double retailPrice = Double.parseDouble("255");
        Double wholesalePrice = Double.parseDouble("100");
        Integer unit = Integer.parseInt(1 + "");
        String calculateUnit = "Cai";
        Float weight = Float.parseFloat("15");
        String description = "Day la mat hang test";
        Integer categoryId = Integer.parseInt(1 + "");
        String attribute = "[{\"atbName\":\"Kích cỡ\",\"atbValue\":\"X\"},{\"atbName\":\"Kích cỡ \",\"atbValue\":\"Y\"}]-[{\"unitName\":\"Thùng\",\"unitValue\":30}]";
        CategoryDAO categoryDAO = new CategoryDAO();
        smhS.categories = categoryDAO.getListCategory();
        Category category = smhS.getCategoryById(categoryId);
        MatHang expectMatHang = new MatHang();
        expectMatHang.setCode(code);
        expectMatHang.setName(name);
        expectMatHang.setImage(image);
        expectMatHang.setRetailPrice(retailPrice);
        expectMatHang.setWholesalePrice(wholesalePrice);
        expectMatHang.setUnit(unit);
        expectMatHang.setCalculateUnit(calculateUnit);
        expectMatHang.setWeight(weight);
        expectMatHang.setDescription(description);
        expectMatHang.setCategory(category);
        expectMatHang.setAttribute(attribute);
        MatHangDAO mhDAO = new MatHangDAO();
        MatHang mahangBD = mhDAO.getMatHangById(111);
        MatHang matHang = smhS.buildMatHang(Long.parseLong(111 + ""), code, name, image, retailPrice,
                wholesalePrice, unit, calculateUnit,
                weight, description, category, attribute);
        boolean check = smhS.daoUpdateWithImage(matHang);
        boolean expected = true;
        assertEquals(expected, check);
        MatHang mathangUpdated = mhDAO.getMatHangById(111);
        boolean roleback = smhS.daoUpdateWithImage(mahangBD);
        assertEquals(matHang.getId(), mathangUpdated.getId());
        assertEquals(matHang.getCode(), mathangUpdated.getCode());
        assertEquals(matHang.getName(), mathangUpdated.getName());
        InputStream i1 = matHang.getImage();
        InputStream i2 = mathangUpdated.getImage();
        boolean checkImg = true;
        try {
            // do the compare
            while (true) {
                int fr = i1.read();
                int tr = i2.read();

                if (fr != tr) {
                    checkImg = false;
                    break;
                }


                if (fr == -1) {
                    checkImg = true;
                    break;
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (i1 != null)
                i1.close();
            if (i2 != null)
                i2.close();
        }
        boolean checkExpected = true;
        assertEquals(checkExpected, checkImg);
        assertEquals(matHang.getRetailPrice(), mathangUpdated.getRetailPrice());
        assertEquals(matHang.getWholesalePrice(), mathangUpdated.getWholesalePrice());
        assertEquals(matHang.getUnit(), mathangUpdated.getUnit());
        assertEquals(matHang.getCalculateUnit(), mathangUpdated.getCalculateUnit());
        assertEquals(matHang.getWeight(), mathangUpdated.getWeight());
        assertEquals(matHang.getDescription(), mathangUpdated.getDescription());
        assertEquals(matHang.getCategory().getName(), mathangUpdated.getCategory().getName());
        assertEquals(matHang.getAttribute(), mathangUpdated.getAttribute());
    }

    @Test
    void tim_mat_hang_theo_ten() {
        MatHangDAO dao = new MatHangDAO();
        // key khong co ket qua nao khop
        String keyword = "xxxxxxxxxx";
        List<MatHang> list = dao.search(keyword);
        assertNotNull(list);
        assertEquals(0, list.size());

        // key co ket qua khop
        keyword = "band";
        list = dao.search(keyword);
        assertNotNull(list);
        assertEquals(4, list.size());
        for (MatHang mh : list) {
            assertTrue(mh.getName().toLowerCase().contains(keyword));
        }
    }

    @Test
    void getall() {
        MatHangDAO matHangDAO = new MatHangDAO();
        List<MatHang> listClient = matHangDAO.getall();
        assertNotNull(listClient);
        assertEquals(97, listClient.size());


        listClient = matHangDAO.getall();
        assertNull(listClient);
        assertEquals(0, listClient.size());

    }

    @Test
    void deleteMH() {
        MatHangDAO matHangDAO = new MatHangDAO();
        Connection con = matHangDAO.con;
        try{
            con.setAutoCommit(false);
            matHangDAO.deleteMH(6);
            MatHang matHang1 = matHangDAO.getMatHangById(6);
            assertNull(matHang1.getId());
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try{
                con.rollback();
                con.setAutoCommit(true);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    @Test
    void searchByName() {
        MatHangDAO matHangDAO = new MatHangDAO();
        //test1
        String txtSearch = "xxxxxxxxxx";
        List<MatHang> list = matHangDAO.searchByName(txtSearch);
        assertNotNull(list);
        assertEquals(0, list.size());

        //exception scenario 2
        txtSearch = "rn";
        list = matHangDAO.searchByName(txtSearch);
        assertNotNull(list);
        assertEquals(0, list.size());

        //standard scenario 1
        txtSearch = "comtinar";
        list = matHangDAO.searchByName(txtSearch);
        assertNotNull(list);
        assertEquals(1, list.size());
        for(int i=0; i<list.size(); i++){
            assertTrue(list.get(i).getName().toLowerCase().contains(txtSearch.toLowerCase()));
        }

        //standard scenario 2
        txtSearch = "com";
        list = matHangDAO.searchByName(txtSearch);
        assertNotNull(list);
        assertEquals(6, list.size());
        for(int i=0; i<list.size(); i++){
            assertTrue(list.get(i).getName().toLowerCase().contains(txtSearch.toLowerCase()));
        }

        txtSearch="";
        list = matHangDAO.searchByName(txtSearch);
        assertNotNull(list);
        assertEquals(96, list.size());
        return;
    }
}