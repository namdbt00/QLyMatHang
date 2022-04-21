package com.example.mathang;

import dao.NhapHangDAO;
import model.DonNhapHang;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "/QuanLyNhapHangFilter", value = "/quan-ly-nhap-hang-filter")
public class QuanlyNhaphangFilterServlet extends HttpServlet {

    private NhapHangDAO nhaphangDAO;

    public void init() {
        nhaphangDAO = new NhapHangDAO();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        ArrayList<DonNhapHang> listDonNhapHang = nhaphangDAO.getListDonNhapHang("filter", "");

        // Gán data
        request.setAttribute("list", listDonNhapHang);
        request.setAttribute("filter", true);


        RequestDispatcher rd = request.getRequestDispatcher("nhaphang/quanlynhaphang.jsp");
        rd.forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String keyword = req.getParameter("keyword");
        ArrayList<DonNhapHang> listDonNhapHang = keyword == null ? nhaphangDAO.getListDonNhapHang("filter", "") : nhaphangDAO.getListDonNhapHang("search-filter", keyword);
        req.setAttribute("list", listDonNhapHang);
        req.setAttribute("filter", true);

        // Chuyển sang page cần nhận data
        RequestDispatcher rd = req.getRequestDispatcher("nhaphang/quanlynhaphang.jsp");
        rd.forward(req, resp);
    }
}
