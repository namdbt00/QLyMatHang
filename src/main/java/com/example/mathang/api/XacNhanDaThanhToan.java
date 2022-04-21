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

@WebServlet(value = "/confirm-payment")
public class XacNhanDaThanhToan extends HttpServlet {
    private static final Gson gson = new GsonBuilder().create();

    @Override
    protected void doPost(
            HttpServletRequest req,
            HttpServletResponse resp
    ) throws IOException {
        Integer id = Integer.valueOf(req.getParameter("id"));
        NhapHangDAO dao = new NhapHangDAO();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.setStatus(200);
        ResponseMessage message;
        if (dao.xacNhanDaThanhToan(id)) {
            message = ResponseMessage.builder()
                    .message("Xác nhận đã thanh toán thành công")
                    .isSuccessful(true).build();
        } else {
            message = ResponseMessage.builder()
                    .message("Xác nhận đã thanh toán thất bại")
                    .isSuccessful(false).build();
        }
        String json = gson.toJson(message);
        resp.getWriter().println(json);
    }
}
