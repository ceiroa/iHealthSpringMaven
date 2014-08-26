package com.ceiroa.ihealth.controller.manage;

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
public class ManageUploads {
	
	private static ClientDAO clientDAO;
	
	private String viewName;
	
	@Autowired
	public void setClientDAO(ClientDAO clientDAO) {
		ManageUploads.clientDAO = clientDAO;
	}

	public void setViewName(String viewName) {
		this.viewName = viewName;
	}
	
	@RequestMapping("manageUploads/view.htm")
    public ModelAndView view(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("active", "manageUploads");

        request.setAttribute("infoMessage", "Search user to uploads files for:");
        
        return new ModelAndView(viewName, null);
    } 

	@RequestMapping("manageUploads/find.htm")
    public ModelAndView find(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("active", "manageUploads");

        List<Client> clients = clientDAO.getClients(request);
             
        request.setAttribute("clients", clients);

        if(clients==null || clients.isEmpty()) {
            request.setAttribute("errorMessage", "No clients found");
        }
        
        return new ModelAndView(viewName, null);
    }
}
