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
        ResponseMessage message;
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
        String json = gson.toJson(message);
        resp.getWriter().println(json);
    }
}