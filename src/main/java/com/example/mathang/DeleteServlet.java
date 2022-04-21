package com.example.mathang;

import dao.MatHangDAO;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "DeleteServlet", value = "/delete")
public class DeleteServlet extends HttpServlet {

protected void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException {
    int id = Integer.parseInt(request.getParameter("id"));
    MatHangDAO dao = new MatHangDAO();
    dao.deleteMH(id);
    response.sendRedirect("trangchu");
}

}
