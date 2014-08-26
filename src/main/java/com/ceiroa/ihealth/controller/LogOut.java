package com.ceiroa.ihealth.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LogOut {

	private String viewName;
	
	public void setViewName(String viewName) {
		this.viewName = viewName;
	}

	@RequestMapping("/logout.htm")
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
        session.invalidate();
        request.setAttribute("successMessage", "Bye bye");
		return new ModelAndView(viewName, null);
	}

}
