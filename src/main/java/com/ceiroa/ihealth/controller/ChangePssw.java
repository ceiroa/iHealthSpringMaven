package com.ceiroa.ihealth.controller;
import com.ceiroa.ihealth.model.daos.AeSimpleMD5;
import com.ceiroa.ihealth.model.daos.UserDAO;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ChangePssw {

	
	private static UserDAO userDAO;
	
	private String viewName;
	
	public void setViewName(String viewName) {
		this.viewName = viewName;
	}
	
	@Autowired
	public void setUserDAO(UserDAO userDAO) {
		ChangePssw.userDAO = userDAO;
	}
	
	@RequestMapping("/changePssw/view.htm")
    public ModelAndView view(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("active", viewName);        
        return new ModelAndView(viewName, null);
    } 

	@RequestMapping("/changePssw/change.htm")
    public ModelAndView change(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
    	request.setAttribute("active", viewName);

        String id = request.getParameter("id");
        String password1 = request.getParameter("password1");
        String password2 = request.getParameter("password2");

        if(!id.isEmpty() && !password1.isEmpty()) {
            if(password1.equals(password2)) {
                try {
                    String encPassword = AeSimpleMD5.MD5(password1);
                    int message = userDAO.updatePassword(Long.parseLong(id), encPassword);

                    if(message!=0) {
                        request.setAttribute("successMessage", "Password successfully updated");
                    } else {
                        request.setAttribute("errorMessage", "Error. Your password could not "
                                + "be updated. Please try again or contact support.");
                    }
                } catch (Exception ex) {
                        Logger.getLogger(ChangePssw.class.getName()).log(Level.SEVERE, null, ex);
                        request.setAttribute("errorMessage", "Your password could not be updated. Please try "
                                + "again later or contact support. (Exception thrown)");
                }
           } else {
                request.setAttribute("errorMessage", "Passwords don't match");
           }
        } else {
            request.setAttribute("errorMessage", "Please fill all fields");
        }

        return new ModelAndView(viewName, null);
    }
}
