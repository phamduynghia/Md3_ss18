
package com.ra.controller;

import com.ra.model.dao.CategoryDAO;
import com.ra.model.dao.CategoryDAOImpl;
import com.ra.model.entity.Category;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "categoryController", value = "/category")
public class CategoryController extends HttpServlet {
    private final CategoryDAO categoryDAO = new CategoryDAOImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action != null) {
            switch (action) {
                case "add":
                    resp.sendRedirect("/view/add-category.jsp");
                    break;
                case "edit":
                    break;
                default:
                    break;
            }
        } else {
            showData(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String name = req.getParameter("name");
        String description = req.getParameter("description");
        boolean status = Boolean.parseBoolean(req.getParameter("status"));
        Category category = new Category();
        category.setCategoryName(name);
        category.setDescription(description);
        category.setStatus(status);
        if (categoryDAO.create(category)) {
            showData(req, resp);
        }
    }

    private void showData(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // gọi đến DAO
        List<Category> categories = categoryDAO.findAll();
        //chuyển dữ liệu sang view
        req.setAttribute("categories", categories);
        req.getRequestDispatcher("/views/category.jsp").forward(req, resp);
    }
}

