package com.example.mathang;

import dao.NhaCungCapDAO;
import model.NhaCungCap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "/ThemNhaCungCap", value = "/them-nha-cung-cap")
public class ThemNhaCungCapServlet extends HttpServlet {

    private NhaCungCapDAO nhaCungCapDAO;
    private NhaCungCap nhaCungCap;

    @Override
    public void init() {
        nhaCungCapDAO = new NhaCungCapDAO();
        nhaCungCap = new NhaCungCap();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        nhaCungCap.setCode(req.getParameter("codeNCC"));
        nhaCungCap.setName(req.getParameter("nameNCC"));
        nhaCungCap.setEmail(req.getParameter("emailNCC"));
        nhaCungCap.setPhone(req.getParameter("phoneNCC"));
        nhaCungCap.setAddress(req.getParameter("addressNCC"));

        boolean result = nhaCungCapDAO.addNhaCungCap(nhaCungCap);

        req.setAttribute("result", result);
        resp.sendRedirect("tao-don-nhap-hang");

//        super.doPost(req, resp);
    }
}
