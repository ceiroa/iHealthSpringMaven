package com.ceiroa.ihealth.controller;

import com.ceiroa.ihealth.model.AccessController;
import com.ceiroa.ihealth.model.User;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class Index {

	private static AccessController accessController;
	
	private String viewName;
	
	public void setViewName(String viewName) {
		this.viewName = viewName;
	}
	
	@Autowired
	public void setAccessController(AccessController accessController) {
		Index.accessController = accessController;
	}

	@RequestMapping("/index/login.htm")
	public ModelAndView login() {       
        return new ModelAndView(viewName, null);
    }

	@RequestMapping("/index/authenticate.htm")
    public ModelAndView authenticate(HttpServletRequest request) {
        //Get params from request
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        User user = null;
        //Find user
        try {
            user = accessController.findUser(username, password);
        } catch (Exception ex) {
            Logger.getLogger(Index.class.getName()).log(Level.SEVERE, null, ex);
        }

        if(user!=null) {
            //Create session
            HttpSession session = request.getSession();
            session.setMaxInactiveInterval(1800);
            session.setAttribute("user", user);

            //Redirect to home page
            return new ModelAndView("home", null);
        } else {
            //Send back to log in page with error message
            request.setAttribute("errorMessage", "Wrong Username <br/> or Password");
            return new ModelAndView("index",null);
        }
    }
}
