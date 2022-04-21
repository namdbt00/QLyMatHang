package com.example.mathang;

import dao.NhapHangDAO;
import model.DonNhap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = "/cap-nhat-don-nhap-hang")
public class CapNhaDonNhapHang extends HttpServlet {

    public void doGet(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException, ServletException {
        String url = "/nhaphang/taodonnhaphang.jsp";
        Integer id = Integer.valueOf(request.getParameter("id"));
        NhapHangDAO dao = new NhapHangDAO();
        DonNhap don = dao.layDonNhapHang(id);
        if (don == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        } else {
            request.setAttribute("isCreate", false);
            request.setAttribute("bill", don);
            RequestDispatcher dispatcher = getServletContext()
                    .getRequestDispatcher(url);
            dispatcher.forward(request, response);
        }
    }
}
