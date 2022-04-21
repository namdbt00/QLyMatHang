package com.example.mathang.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dao.MatHangDAO;
import model.MatHang;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(value = "/search-product")
public class TimMatHang extends HttpServlet {
    private static final Gson gson = new GsonBuilder().create();

    @Override
    protected void doGet(
            HttpServletRequest req,
            HttpServletResponse resp
    ) throws ServletException, IOException {
        MatHangDAO dao = new MatHangDAO();
        String name = req.getParameter("name");
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.setStatus(200);
        List<MatHang> list = dao.search(name);
        String json = gson.toJson(list);
        resp.getWriter().println(json);
    }
}