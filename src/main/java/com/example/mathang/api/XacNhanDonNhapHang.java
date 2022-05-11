package com.example.mathang.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dao.NhapHangDAO;
import model.ResponseMessage;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = "/confirm-bill")
public class XacNhanDonNhapHang extends HttpServlet {
    private static final Gson gson = new GsonBuilder().create();

    @Override
    protected void doPost(
            HttpServletRequest req,
            HttpServletResponse resp
    ) throws IOException {
        Integer id = Integer.valueOf(req.getParameter("id"));
        Long importTime = Long.valueOf(req.getParameter("importTime"));
        Long paymentTime = Long.valueOf(req.getParameter("paymentTime"));
        NhapHangDAO dao = new NhapHangDAO();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.setStatus(200);
        ResponseMessage message;
        if (dao.xacNhanDonNhapHang(id, paymentTime, importTime)) {
            message = ResponseMessage.builder()
                    .message("Đơn nhập hàng đã duyệt thành công")
                    .isSuccessful(true).build();
        } else {
            message = ResponseMessage.builder()
                    .message("Đơn nhập hàng đã duyệt thất bại")
                    .isSuccessful(false).build();
        }
        String json = gson.toJson(message);
        resp.getWriter().println(json);
    }
}
