package com.example.mathang;

import model.DonNhapHang;
import dao.NhapHangDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

// value là route url
@WebServlet(name = "/QuanLyNhapHang", value = "/quan-ly-nhap-hang")
public class QuanlyNhaphangServlet extends HttpServlet {

    private NhapHangDAO nhaphangDAO;

    public void init() {
        nhaphangDAO = new NhapHangDAO();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        ArrayList<DonNhapHang> listDonNhapHang = nhaphangDAO.getListDonNhapHang("default", "");

        // Gán data
        request.setAttribute("list", listDonNhapHang);
        request.setAttribute("filter", false);

        // Chuyển sang page cần nhận data
        RequestDispatcher rd = request.getRequestDispatcher("nhaphang/quanlynhaphang.jsp");
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String keyword = req.getParameter("keyword");
        ArrayList<DonNhapHang> listDonNhapHang = keyword == null ? nhaphangDAO.getListDonNhapHang("default", "") : nhaphangDAO.getListDonNhapHang("search", keyword);
        req.setAttribute("list", listDonNhapHang);
        req.setAttribute("filter", false);

        // Chuyển sang page cần nhận data
        RequestDispatcher rd = req.getRequestDispatcher("nhaphang/quanlynhaphang.jsp");
        rd.forward(req, resp);
    }


    public void destroy() {
    }
}
