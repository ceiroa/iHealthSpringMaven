package com.ceiroa.ihealth.controller.manage;

import com.ceiroa.ihealth.model.User;
import com.ceiroa.ihealth.model.daos.UserDAO;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ManageUsers {

	private static UserDAO userDAO;
	
	private String viewName;
	
	@Autowired
	public void setUserDAO(UserDAO userDAO) {
		ManageUsers.userDAO = userDAO;
	}

	public void setViewName(String viewName) {
		this.viewName = viewName;
	}
	
	@RequestMapping("manageUsers/view.htm")
    public ModelAndView view(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("active", "manageUsers");

        //Retrieve all users from database
        List<User> users = userDAO.getUsers();
        request.setAttribute("users", users);
        
        return new ModelAndView(viewName, null);
    } 

	@RequestMapping("manageUsers/update.htm")
    public ModelAndView update(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
    	request.setAttribute("active", "manageUsers");
    	
        //If the request is to deactivate a merchant
        if(!request.getParameter("id").equals("0")
                && request.getParameter("action").equals("deactivate")) {
            userDAO.deactivate(Long.parseLong(request.getParameter("id")));
        }

        if(!request.getParameter("id").equals("0")
                && request.getParameter("action").equals("activate")) {
            userDAO.activate(Long.parseLong(request.getParameter("id")));
        }

        //Retrieve all users from database
        List<User> users = userDAO.getUsers();
        request.setAttribute("users", users);
        
        return new ModelAndView(viewName, null);
    }
}
