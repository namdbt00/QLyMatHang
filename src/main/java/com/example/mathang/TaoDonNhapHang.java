package com.example.mathang;

import dao.NhapHangDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = "/tao-don-nhap-hang")
public class TaoDonNhapHang extends HttpServlet {

    public void doGet(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException, ServletException {
        String url = "/nhaphang/taodonnhaphang.jsp";
        NhapHangDAO dao = new NhapHangDAO();
        String code = dao.getNewCode();
        request.setAttribute("code", code);
        request.setAttribute("isCreate", true);
        RequestDispatcher dispatcher = getServletContext()
                .getRequestDispatcher(url);
        dispatcher.forward(request, response);
    }
}
