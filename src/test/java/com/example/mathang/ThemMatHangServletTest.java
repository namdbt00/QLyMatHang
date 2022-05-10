package com.example.mathang;

import dao.CategoryDAO;
import dao.MatHangDAO;
import model.Atb;
import model.Category;
import model.ConversionUnit;
import model.MatHang;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ThemMatHangServletTest {

    @Test
    void doGet() {
    }

    @Test
    void doPost() {
    }

    @Test
    void insertMatHang() {
    }

    @Test
    void testGetCategoryById() {
        ThemMatHangServlet tmhS = new ThemMatHangServlet();
        Category expectCategory = new Category();
        expectCategory.setCategoryId(1);
        expectCategory.setName("Nội thất");
        CategoryDAO categoryDAO = new CategoryDAO();
        tmhS.categories = categoryDAO.getListCategory();
        Category category = tmhS.getCategoryById(1);
        assertEquals(category.getName(), expectCategory.getName());
    }

    @Test
    void testBuildMatHang() {
        String code = "ABC";
        String name = "Ao phong";
        InputStream image = null;
        Double retailPrice = Double.parseDouble("255");
        Double wholesalePrice = Double.parseDouble("100");
        Integer unit = Integer.parseInt(1+"");
        String calculateUnit = "Cai";
        Float weight = Float.parseFloat("15");
        String description = "Day la mat hang test";
        Integer categoryId = Integer.parseInt(1+"");
        String attribute = "[{\"atbName\":\"Kích cỡ\",\"atbValue\":\"X\"},{\"atbName\":\"Kích cỡ \",\"atbValue\":\"Y\"}]-[{\"unitName\":\"Thùng\",\"unitValue\":30}]";
        ThemMatHangServlet tmhS = new ThemMatHangServlet();
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

        assertEquals(matHang.getId(), expectMatHang.getId());
        assertEquals(matHang.getCode(), expectMatHang.getCode());
        assertEquals(matHang.getName(), expectMatHang.getName());
        assertEquals(matHang.getRetailPrice(), expectMatHang.getRetailPrice());
        assertEquals(matHang.getWholesalePrice(), expectMatHang.getWholesalePrice());
        assertEquals(matHang.getUnit(), expectMatHang.getUnit());
        assertEquals(matHang.getCalculateUnit(), expectMatHang.getCalculateUnit());
        assertEquals(matHang.getWeight(), expectMatHang.getWeight());
        assertEquals(matHang.getDescription(), expectMatHang.getDescription());
        assertEquals(matHang.getCategory().getName(), expectMatHang.getCategory().getName());
        assertEquals(matHang.getAttribute(), expectMatHang.getAttribute());
        assertEquals(matHang.getImage(), expectMatHang.getImage());
    }

    @Test
    void testToUTF8String() {
        ThemMatHangServlet tmhS = new ThemMatHangServlet();
        String expectedString = "Đây là xâu hương ơ";
        String str = tmhS.toUTF8String("Đây là xâu hương ơ");
        assertEquals(expectedString.length(), str.length());
    }

    @Test
    void testCreateAttributeString() {
        ThemMatHangServlet tmhS = new ThemMatHangServlet();
        String expectedString = "[{\"atbName\":\"Kích cỡ\",\"atbValue\":\"X\"},{\"atbName\":\"Kích cỡ \",\"atbValue\":\"Y\"}]-" +
                "[{\"unitName\":\"Thùng\",\"unitValue\":30}]";
        List<Atb> atbs = new ArrayList<>();
        List<ConversionUnit> units = new ArrayList<>();

        atbs.add(new Atb("Kích cỡ", "X"));
        atbs.add(new Atb("Kích cỡ ", "Y" ));
        units.add(new ConversionUnit("Thùng", 30));
        String str = tmhS.createAttributeString(atbs, units);

        assertEquals(expectedString.length(), str.length());
    }

    @Test
    void testDaoProcesssing() {
        ThemMatHangServlet tmhS = new ThemMatHangServlet();
        String code = "ABC";
        String name = "Ao phong";
        InputStream image = null;
        Double retailPrice = Double.parseDouble("255");
        Double wholesalePrice = Double.parseDouble("100");
        Integer unit = Integer.parseInt(1+"");
        String calculateUnit = "Cai";
        Float weight = Float.parseFloat("15");
        String description = "Day la mat hang test";
        Integer categoryId = Integer.parseInt(1+"");
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

        boolean check = tmhS.daoProcesssing(matHang);
        boolean expected = true;
        assertEquals(expected, check);
        MatHangDAO daoMH = new MatHangDAO();
        List<MatHang> listMH = daoMH.getall();
        assertEquals(matHang.getCode(), listMH.get(0).getCode());
        int idMH = Integer.parseInt(listMH.get(0).getId()+"");
        daoMH.deleteMH(idMH);
    }
}
