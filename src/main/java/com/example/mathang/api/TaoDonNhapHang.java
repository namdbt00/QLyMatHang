package com.example.mathang.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dao.NhapHangDAO;
import model.DonNhap;
import model.ResponseMessage;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

@WebServlet(value = "/create-bill")
public class TaoDonNhapHang extends HttpServlet {
    private static final Gson gson = new GsonBuilder().create();

    @Override
    protected void doPost(
            HttpServletRequest req,
            HttpServletResponse resp
    ) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.setStatus(200);
        ResponseMessage message = null;
        StringBuilder sb = new StringBuilder();
        String line;
        BufferedReader reader = req.getReader();
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        DonNhap body = gson.fromJson(
                sb.toString(),
                DonNhap.class
        );
        NhapHangDAO dao = new NhapHangDAO();
        if (body.getProviderId() == null) {
            message = ResponseMessage.builder()
                    .message("Thông tin nhà cung cấp không hợp lệ!")
                    .isSuccessful(false).build();
        } else if (body.getProducts() == null || body.getProducts().isEmpty()) {
            message = ResponseMessage.builder()
                    .message("Danh sách sản phẩm không được bỏ trống!")
                    .isSuccessful(false).build();
        } else {
            for (DonNhap.Product product : body.getProducts()) {
                if (product.getQuantity() == null || product.getQuantity() == 0 ||
                        product.getPrice() == null || product.getPrice() == 0) {
                    message = ResponseMessage.builder()
                            .message("Giá nhập hoặc số lượng sản phẩm không hợp lệ!")
                            .isSuccessful(false).build();
                    break;
                }
            }
            if (message == null) {
                Integer id = dao.createNew(body);
                if (id != -1) {
                    message = ResponseMessage.builder()
                            .message("Tạo đơn nhập hàng thành công")
                            .isSuccessful(true).id(id).build();
                } else {
                    message = ResponseMessage.builder()
                            .message("Tạo đơn nhập hàng thất bại")
                            .isSuccessful(false).build();
                }
            }
        }
        String json = gson.toJson(message);
        resp.getWriter().println(json);
    }
}