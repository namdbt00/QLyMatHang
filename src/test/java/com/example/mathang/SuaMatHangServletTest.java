package com.example.mathang;

import dao.CategoryDAO;
import model.Atb;
import model.Category;
import model.ConversionUnit;
import model.MatHang;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SuaMatHangServletTest {

    @Test
    void doGet() {
    }

    @Test
    void doPost() {
    }

    @Test
    void testCreateListAtbFromJsonString() {
        SuaMatHangServlet smhS = new SuaMatHangServlet();
        String strJSON = "[{\"atbName\":\"Kích cỡ\",\"atbValue\":\"X\"},{\"atbName\":\"Kích cỡ \",\"atbValue\":\"Y\"}]";
        ArrayList<Atb> listAtb = smhS.createListAtbFromJsonString(strJSON);
        ArrayList<Atb> expectedList = new ArrayList<>();
        expectedList.add(new Atb("Kích cỡ", "X"));
        expectedList.add(new Atb("Kích cỡ ", "Y"));
        assertEquals(listAtb.size(), expectedList.size());
        assertEquals(listAtb.get(0).getAtbName(), expectedList.get(0).getAtbName());
        assertEquals(listAtb.get(0).getAtbValue(), expectedList.get(0).getAtbValue());
    }

    @Test
    void testCreateListUnitFromJsonString() {
        SuaMatHangServlet smhS = new SuaMatHangServlet();
        String strJSON = "[{\"unitName\":\"Thùng\",\"unitValue\":30}]";
        ArrayList<ConversionUnit> listConversionUnit = smhS.createListUnitFromJsonString(strJSON);
        ArrayList<ConversionUnit> expectedList = new ArrayList<>();
        expectedList.add(new ConversionUnit("Thùng", 30));
        assertEquals(listConversionUnit.size(), expectedList.size());
        assertEquals(listConversionUnit.get(0).getUnitName(), expectedList.get(0).getUnitName());
        assertEquals(listConversionUnit.get(0).getUnitValue(), expectedList.get(0).getUnitValue());
    }

    @Test
    void updateMatHang() {
    }

    @Test
    void testGetCategoryById() {
        SuaMatHangServlet smhS = new SuaMatHangServlet();
        Category expectCategory = new Category();
        expectCategory.setCategoryId(1);
        expectCategory.setName("Nội thất");
        CategoryDAO categoryDAO = new CategoryDAO();
        smhS.categories = categoryDAO.getListCategory();
        Category category = smhS.getCategoryById(1);
        assertEquals(category.getName(), expectCategory.getName());
    }

    @Test
    void testBuildMatHang() {
        Long id = Long.parseLong(1 + "");
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
        SuaMatHangServlet smhS = new SuaMatHangServlet();
        CategoryDAO categoryDAO = new CategoryDAO();
        smhS.categories = categoryDAO.getListCategory();
        Category category = smhS.getCategoryById(categoryId);
        MatHang expectMatHang = new MatHang();
        expectMatHang.setId(id);
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

        MatHang matHang = smhS.buildMatHang(id, code, name, image, retailPrice,
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
    void testBuildMatHangWithoutImage() {
        Long id = Long.parseLong(1 + "");
        String code = "ABC";
        String name = "Ao phong";
        Double retailPrice = Double.parseDouble("255");
        Double wholesalePrice = Double.parseDouble("100");
        Integer unit = Integer.parseInt(1 + "");
        String calculateUnit = "Cai";
        Float weight = Float.parseFloat("15");
        String description = "Day la mat hang test";
        Integer categoryId = Integer.parseInt(1 + "");
        String attribute = "[{\"atbName\":\"Kích cỡ\",\"atbValue\":\"X\"},{\"atbName\":\"Kích cỡ \",\"atbValue\":\"Y\"}]-[{\"unitName\":\"Thùng\",\"unitValue\":30}]";
        SuaMatHangServlet smhS = new SuaMatHangServlet();
        CategoryDAO categoryDAO = new CategoryDAO();
        smhS.categories = categoryDAO.getListCategory();
        Category category = smhS.getCategoryById(categoryId);
        MatHang expectMatHang = new MatHang();
        expectMatHang.setId(id);
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

        MatHang matHang = smhS.buildMatHangWithoutImage(id, code, name, retailPrice,
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
    }

    @Test
    void testToUTF8String() {
        SuaMatHangServlet smhS = new SuaMatHangServlet();
        String expectedString = "Đây là xâu hương ơ";
        String str = smhS.toUTF8String("Đây là xâu hương ơ");
        assertEquals(expectedString.length(), str.length());
    }
}