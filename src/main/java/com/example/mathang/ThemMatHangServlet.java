package com.example.mathang;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dao.CategoryDAO;
import dao.MatHangDAO;
import model.Atb;
import model.Category;
import model.ConversionUnit;
import model.MatHang;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebServlet(name = "themMathangServlet", value = "/them-mat-hang")
@MultipartConfig(maxFileSize = 16177216)//1.5mb
public class ThemMatHangServlet extends HttpServlet {
    public static final int DEFAULT_BUFFER_SIZE = 8192;
    public List<Category> categories = null;
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        showNewForm(request, response);
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            insertMatHang(request, response);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String url = "";
        url = "/themmathang/themmathang.jsp";

        CategoryDAO categoryDAO = new CategoryDAO();
        categories = categoryDAO.getListCategory();

        for(int i = 0; i < categories.size(); i++) {
            System.out.println(categories.get(i).getName());
        }
        request.setAttribute("listCategory", categories);

        // forward request and response to the view
        RequestDispatcher dispatcher
                = getServletContext().getRequestDispatcher(url);
        dispatcher.forward(request, response);
    }

    public void insertMatHang(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {

        String code = toUTF8String(request.getParameter("productid"));
        String name = toUTF8String(request.getParameter("productname"));
        Part part = request.getPart("img");
        InputStream image = part.getInputStream();
        Double retailPrice = Double.parseDouble(request.getParameter("productretailprice"));
        Double wholesalePrice = Double.parseDouble(request.getParameter("productwholesaleprice"));
        Integer unit = Integer.parseInt(request.getParameter("weightunit"));
        String calculateUnit = toUTF8String(request.getParameter("productunit"));
        Float weight = Float.parseFloat(request.getParameter("productweight"));
        String description = toUTF8String(request.getParameter("ifodesscription"));
        Integer categoryId = Integer.parseInt(request.getParameter("productcategory"));
        String attribute = toUTF8String(request.getParameter("attributeJSON"));
        int numRowAtb = Integer.parseInt(request.getParameter("numatb"));
        int numRowUnit = Integer.parseInt(request.getParameter("numunit"));

        attribute = createAttributeString(numRowAtb, numRowUnit, request);

        Category category = getCategoryById(categoryId);
        MatHang mathang = buildMatHang(code, name, image, retailPrice,
                wholesalePrice, unit, calculateUnit,
                weight, description, category, attribute);

        boolean checkdao = daoProcesssing(mathang);

        if(!checkdao){
            String htmlMessage = "Mã mặt hàng đã tồn tại";
            String check = "1";

            ServletContext sc = request.getServletContext();
            sc.setAttribute("messages", htmlMessage);
            sc.setAttribute("check", check);
            response.sendRedirect("/MatHang/them-mat-hang");
        }else {
            String htmlMessage = "Thêm thành công";
            String check = "1";

            ServletContext sc = request.getServletContext();
            sc.setAttribute("messages", htmlMessage);
            sc.setAttribute("check", check);
            response.sendRedirect("/MatHang/them-mat-hang");
        }

    }

    private String createAttributeString(int numRowAtb, int numRowUnit, HttpServletRequest request) {
        String attribute = "";
        List<Atb> atbs =new ArrayList<>();
        List<ConversionUnit> units = new ArrayList<>();
        for(int i = 0; i < numRowAtb; i++){
            int k = i+1;
            String atbName1 = request.getParameter("atbName"+k);
            String atbValue1 = request.getParameter("atbValue"+k);
            Atb atb = new Atb(atbName1, atbValue1);
            atbs.add(atb);
        }
        for(int i = 0; i < numRowUnit; i++){
            int k = i+1;
            String unitName1 = request.getParameter("unitName"+k);
            Integer unitValue1 = Integer.parseInt(request.getParameter("unitValue"+k));
            ConversionUnit unit = new ConversionUnit(unitName1, unitValue1);
            units.add(unit);
        }
        String jsonAtb = new Gson().toJson(atbs);
        String jsonUnit = new Gson().toJson(units);

        attribute = jsonAtb + "-" + jsonUnit;
        return toUTF8String(attribute);
    }

    public Category getCategoryById(Integer categoryId){
        Category category = new Category();
        for(int i = 0; i < categories.size(); i++){
            if(categories.get(i).getCategoryId() == categoryId) {
                category = categories.get(i);
            }
        }
        return category;
    }

    public MatHang buildMatHang(String code, String name,InputStream image, Double retailPrice,
                                Double wholesalePrice, Integer unit, String calculateUnit,
                                Float weight, String description, Category category, String attribute) {
        MatHang mathang = MatHang.builder()
                .code(code)
                .name(name)
                .image(image)
                .retailPrice(retailPrice)
                .wholesalePrice(wholesalePrice)
                .unit(unit)
                .calculateUnit(calculateUnit)
                .weight(weight)
                .description(description)
                .category(category)
                .attribute(attribute)
                .build();
        return mathang;
    }

    private boolean daoProcesssing(MatHang mathang) {
        MatHangDAO matHangDAO = new MatHangDAO();
        boolean check = matHangDAO.saveMatHang(mathang);
        return check;
    }

    public String toUTF8String(String string){
        String result = "";
        byte[] bytes = string.getBytes(StandardCharsets.ISO_8859_1);
        result = new String(bytes, StandardCharsets.UTF_8);
        return result;
    }
}
