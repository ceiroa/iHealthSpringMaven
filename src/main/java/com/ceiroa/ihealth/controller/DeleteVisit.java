package com.ceiroa.ihealth.controller;

import com.ceiroa.ihealth.model.forms.daos.VisitHelperDAO;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DeleteVisit {

	private static VisitHelperDAO visitHelperDAO;
	
	private String viewName;
	
	public void setViewName(String viewName) {
		this.viewName = viewName;
	}
	
	@Autowired
	public void setVisitHelperDAO(VisitHelperDAO visitHelperDAO) {
		DeleteVisit.visitHelperDAO = visitHelperDAO;
	}


	@RequestMapping("/deleteVisit/delete.htm")
    public ModelAndView delete(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        request.setAttribute("active", "visits");

        String visitId = request.getParameter("visitId");
        String tableName = request.getParameter("controller") + "Visits";

        int success = visitHelperDAO.deleteVisit(visitId, tableName);
        if(success==1) {
                request.setAttribute("successMessage", "Visit successfully deleted");
        } else {
                request.setAttribute("errorMessage", "Couldn't delete visit. Please try"
                    + " again later or contact support.");
        }
        
        return new ModelAndView(viewName, null);
    }
}
