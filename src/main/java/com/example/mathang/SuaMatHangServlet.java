package com.example.mathang;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
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
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;


@WebServlet(name = "suaMathangServlet", value = "/sua-mat-hang")
@MultipartConfig(maxFileSize = 16177216)//1.5mb
public class SuaMatHangServlet extends HttpServlet {
    public static final int DEFAULT_BUFFER_SIZE = 8192;
    public List<Category> categories = null;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        showEditForm(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            updateMatHang(request, response);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//        int id = Integer.parseInt(request.getParameter("id"));
//        User existingUser = userDAO.selectUser(id);
//        RequestDispatcher dispatcher = request.getRequestDispatcher("user-form.jsp");
//        request.setAttribute("user", existingUser);
//        dispatcher.forward(request, response);
        String url = "";
        url = "/themmathang/suamathang.jsp";
        MatHangDAO matHangDAO = new MatHangDAO();
        int id = Integer.parseInt(request.getParameter("idEdit"));
//        System.out.println("Edit id: " + id);
        MatHang matHang = matHangDAO.getMatHangById(id);
//        System.out.println("Id mat hang: " + matHang.getId());

        CategoryDAO categoryDAO = new CategoryDAO();
        categories = categoryDAO.getListCategory();

//        for (int i = 0; i < categories.size(); i++) {
//            System.out.println(categories.get(i).getName());
//        }

        // json to list
        if (matHang.getAttribute() != null && !matHang.getAttribute().equals("")) {
            String[] tokens = matHang.getAttribute().split("-");
            List<Atb> listAtb = createListAtbFromJsonString(tokens[0]);
            List<ConversionUnit> listUnit = createListUnitFromJsonString(tokens[1]);
//            System.out.println(matHang.getAttribute());
//            System.out.println(tokens[0]);
//            System.out.println(tokens[1]);
//            System.out.println("List unit:  " + listUnit.size());
            request.setAttribute("listAtb", listAtb);
            request.setAttribute("listUnit", listUnit);
        }
        // gan vao request
        request.setAttribute("listCategory", categories);
        request.setAttribute("matHang", matHang);

        try {
            byte[] byteArray = getByteArray(matHang.getImage());
            String base64String = Base64.getEncoder().encodeToString(byteArray);
            request.setAttribute("imageBase64", base64String);
        } catch (Exception e) {
            e.printStackTrace();
        }

//        ServletContext sc = request.getServletContext();
//        String htmlMessage = "";
//        sc.setAttribute("messages", htmlMessage);

        // forward request and response to the view
        RequestDispatcher dispatcher
                = getServletContext().getRequestDispatcher(url);
        dispatcher.forward(request, response);

    }

    private static byte[] getByteArray(InputStream is) throws Exception {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        BufferedOutputStream os = new BufferedOutputStream(b);
        while (true) {
            int i = is.read();
            if (i == -1) break;
            os.write(i);
        }
        os.flush();
        os.close();
        return b.toByteArray();
    }

    public ArrayList<Atb> createListAtbFromJsonString(String json) {
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<Atb> result = null;
        try {
            result = new ArrayList<Atb>(Arrays.asList(mapper.readValue(json, Atb[].class)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public ArrayList<ConversionUnit> createListUnitFromJsonString(String json) {
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<ConversionUnit> result = null;
        try {
            result = new ArrayList<ConversionUnit>(Arrays.asList(mapper.readValue(json, ConversionUnit[].class)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void updateMatHang(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        Long id = Long.parseLong(request.getParameter("idMatHang"));
        String code = toUTF8String(request.getParameter("productid"));
        String name = toUTF8String(request.getParameter("productname"));
        Part part = request.getPart("img");
        System.out.println(part.getContentType());
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

        List<Atb> atbs = new ArrayList<>();
        List<ConversionUnit> units = new ArrayList<>();
        for (int i = 0; i < numRowAtb; i++) {
            int k = i + 1;
            String atbName1 = request.getParameter("atbName" + k);
            String atbValue1 = request.getParameter("atbValue" + k);
            Atb atb = new Atb(atbName1, atbValue1);
            atbs.add(atb);
        }
        for (int i = 0; i < numRowUnit; i++) {
            int k = i + 1;
            String unitName1 = request.getParameter("unitName" + k);
            Integer unitValue1 = Integer.parseInt(request.getParameter("unitValue" + k));
            ConversionUnit unitt = new ConversionUnit(unitName1, unitValue1);
            units.add(unitt);
        }


        attribute = createAttributeString(atbs, units);
        Category category = getCategoryById(categoryId);
        MatHang mathang;
        InputStream is = part.getInputStream();
        System.out.println("available : " + is.available());
        boolean checkdao = true;
        if (is.available() != 0) {
            System.out.println("Include image");
            InputStream image = part.getInputStream();
            mathang = buildMatHang(id, code, name, image, retailPrice,
                    wholesalePrice, unit, calculateUnit,
                    weight, description, category, attribute);
            checkdao = daoUpdateWithImage(mathang);
        } else {
            System.out.println("Not include image");
            mathang = buildMatHangWithoutImage(id, code, name, retailPrice,
                    wholesalePrice, unit, calculateUnit,
                    weight, description, category, attribute);
            checkdao = daoUpdateWithoutImage(mathang);

        }

        if (checkdao) {
            String htmlMessage = "Sửa thành công";
            String check = "1";

            ServletContext sc = request.getServletContext();
            sc.setAttribute("messages", htmlMessage);
            sc.setAttribute("check", check);
            response.sendRedirect("/MatHang/sua-mat-hang?idEdit=" + id);
        } else {
            String htmlMessage = "Mã mặt hàng đã tồn tại";
            String check = "1";

            ServletContext sc = request.getServletContext();
            sc.setAttribute("messages", htmlMessage);
            sc.setAttribute("check", check);
            response.sendRedirect("/MatHang/sua-mat-hang?idEdit=" + id);
        }


    }

    public String createAttributeString(List<Atb> atbs, List<ConversionUnit> units) {
        String attribute = "";

        String jsonAtb = new Gson().toJson(atbs);
        String jsonUnit = new Gson().toJson(units);

        attribute = jsonAtb + "-" + jsonUnit;
        return toUTF8String(attribute);
    }

    public Category getCategoryById(Integer categoryId) {
        Category category = new Category();
        for (int i = 0; i < categories.size(); i++) {
            if (categories.get(i).getCategoryId() == categoryId) {
                category = categories.get(i);
            }
        }
        return category;
    }

    public MatHang buildMatHang(Long id, String code, String name, InputStream image, Double retailPrice,
                                Double wholesalePrice, Integer unit, String calculateUnit,
                                Float weight, String description, Category category, String attribute) {
        MatHang mathang = MatHang.builder()
                .id(id)
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

    public MatHang buildMatHangWithoutImage(Long id, String code, String name, Double retailPrice,
                                            Double wholesalePrice, Integer unit, String calculateUnit,
                                            Float weight, String description, Category category, String attribute) {
        MatHang mathang = MatHang.builder()
                .id(id)
                .code(code)
                .name(name)
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

    public boolean daoUpdateWithImage(MatHang mathang) {
        MatHangDAO matHangDAO = new MatHangDAO();
        boolean check = matHangDAO.updateMatHangWithImage(mathang);
        return check;
    }

    public boolean daoUpdateWithoutImage(MatHang mathang) {
        MatHangDAO matHangDAO = new MatHangDAO();
        boolean check = matHangDAO.updateMatHangWithoutImage(mathang);
        return check;
    }

    public String toUTF8String(String string) {
        String result = "";
        byte[] bytes = string.getBytes(StandardCharsets.ISO_8859_1);
        result = new String(bytes, StandardCharsets.UTF_8);
        return result;
    }
}
