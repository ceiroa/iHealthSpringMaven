package com.ceiroa.ihealth.controller.newitem;

import com.ceiroa.ihealth.model.User;
import com.ceiroa.ihealth.model.daos.AeSimpleMD5;
import com.ceiroa.ihealth.model.daos.UserDAO;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class NewUser {

	private static UserDAO userDAO;
	
	private String viewName;
	
	public void setViewName(String viewName) {
		this.viewName = viewName;
	}
	
	@Autowired
    public void setUserDAO(UserDAO userDAO) {
		NewUser.userDAO = userDAO;
	}

	@RequestMapping("newUser/view.htm")
	public ModelAndView view(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("active", "manageUsers");
        return new ModelAndView(viewName, null);
    } 

	@RequestMapping("newUser/create.htm")
    public ModelAndView create(HttpServletRequest request, HttpServletResponse response) {
        
        @SuppressWarnings("unused")
		String url;
        request.setAttribute("active", "manageUsers");

        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String username = request.getParameter("username");
        String password1 = request.getParameter("password1");
        String password2 = request.getParameter("password2");
        String email = request.getParameter("email");
        String userType = request.getParameter("userType");
        String status = request.getParameter("status");

        if(!firstName.isEmpty() && !lastName.isEmpty() &&
                !username.isEmpty() && !password1.isEmpty() &&
                !email.isEmpty()) {
            if(password1.equals(password2)) {
            	if(!userDAO.usernameExists(username)) {
	                try {
	                    String encPassword = AeSimpleMD5.MD5(password1);
	
	                    int success = userDAO.insert(firstName, lastName,
	                            username, encPassword, userType, email,
	                            status);
	
	                    if(success==1)
	                        request.setAttribute("successMessage", "User successfully created");
	                    else
	                        request.setAttribute("errorMessage", "Couldn't create merchant. Please try"
	                            + "again later or contact support.");
	
	                    //Retrieve all users from database
	                    List<User> users = userDAO.getUsers();
	                    request.setAttribute("users", users);
	
	                } catch (Exception ex) {
	                    Logger.getLogger(NewUser.class.getName()).log(Level.SEVERE, null, ex);
	                    request.setAttribute("errorMessage", "Couldn't create merchant. Please try "
	                            + "again later or contact support. (Exception thrown)");
	                }
	                 url = "/WEB-INF/jsp/manage/manageUsers.jsp";
            	} else {
            		refillFields(request);
                    request.setAttribute("errorMessage", "Username already exist. Please choose a different one.");
                    url = "/WEB-INF/jsp/new/newUser.jsp";
            	}
            } else {
            	refillFields(request);
                request.setAttribute("errorMessage", "Passwords don't match");
                url = "/WEB-INF/jsp/new/newUser.jsp";
            }
        } else {
        	refillFields(request);
            request.setAttribute("errorMessage", "Please fill all fields");
            url = "/WEB-INF/jsp/new/newUser.jsp";
        }
        
        return new ModelAndView(viewName, null);
    }
    
    private void refillFields(HttpServletRequest request) {
    	request.setAttribute("firstName", request.getParameter("firstName"));
        request.setAttribute("lastName", request.getParameter("lastName"));
        request.setAttribute("username", request.getParameter("username"));
        request.setAttribute("email", request.getParameter("email"));
    }

}
