package com.example.mathang;

import dao.MatHangDAO;
import model.DonNhapHang;
import model.MatHang;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name="SearchServlet", urlPatterns ="/search")
public class SearchMHServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String txtSearch = request.getParameter("txt");
        MatHangDAO mh = new MatHangDAO();
        List<MatHang> list = mh.searchByName(txtSearch);
        request.setAttribute("data",list);
        request.getRequestDispatcher("trangchu.jsp").forward(request,response);
    }

}