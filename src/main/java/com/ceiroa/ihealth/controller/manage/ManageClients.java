package com.ceiroa.ihealth.controller.manage;

import com.ceiroa.ihealth.model.Client;
import com.ceiroa.ihealth.model.daos.ClientDAO;

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
public class ManageClients {

	private static ClientDAO clientDAO;

	private String viewName;
	
	@Autowired
	public void setClientDAO(ClientDAO clientDAO) {
		ManageClients.clientDAO = clientDAO;
	}

	public void setViewName(String viewName) {
		this.viewName = viewName;
	}
	
	@RequestMapping("manageClients/view.htm")
    public ModelAndView view(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("active", "manageClients");
        request.setAttribute("infoMessage", "Search for clients to view or edit:");     
        return new ModelAndView(viewName, null);
    } 

	@RequestMapping("manageClients/find.htm")
    public ModelAndView find(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        request.setAttribute("active", "manageClients");

        List<Client> clients = clientDAO.getClients(request);

        if(clients==null || clients.isEmpty()) {
            request.setAttribute("errorMessage", "No clients found");
        }
        
        return new ModelAndView(viewName, null);
    }
}
