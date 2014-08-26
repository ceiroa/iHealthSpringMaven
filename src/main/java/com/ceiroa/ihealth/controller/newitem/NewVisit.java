package com.ceiroa.ihealth.controller.newitem;

import com.ceiroa.ihealth.model.Client;
import com.ceiroa.ihealth.model.daos.ClientDAO;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class NewVisit { 

	private static ClientDAO clientDAO;
	
	private String viewName;
	
	@Autowired
	public void setClientDAO(ClientDAO clientDAO) {
		NewVisit.clientDAO = clientDAO;
	}

	public void setViewName(String viewName) {
		this.viewName = viewName;
	}
	
	@RequestMapping("newVisit/view.htm")
    public ModelAndView view(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("active", "newVisit");

        request.setAttribute("infoMessage", "Search user to create a visit for:");
        
        return new ModelAndView(viewName, null);
    } 

	@RequestMapping("newVisit/find.htm")
    public ModelAndView newVisit(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("active", "newVisit");

        List<Client> clients = clientDAO.getClients(request);
             
        request.setAttribute("clients", clients);

        if(clients==null || clients.isEmpty()) {
            request.setAttribute("errorMessage", "No clients found");
        }
        
        return new ModelAndView(viewName, null);
    }
}
