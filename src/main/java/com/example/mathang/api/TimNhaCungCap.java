package com.example.mathang.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dao.NhaCungCapDAO;
import model.NhaCungCap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(value = "/search-provider")
public class TimNhaCungCap extends HttpServlet {
    private static final Gson gson = new GsonBuilder().create();

    @Override
    protected void doGet(
            HttpServletRequest req,
            HttpServletResponse resp
    ) throws ServletException, IOException {
        NhaCungCapDAO dao = new NhaCungCapDAO();
        String name = req.getParameter("name");
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.setStatus(200);
        List<NhaCungCap> list = dao.search(name);
        String json = gson.toJson(list);
        resp.getWriter().println(json);
    }
}