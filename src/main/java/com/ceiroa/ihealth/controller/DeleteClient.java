package com.ceiroa.ihealth.controller;

import java.io.IOException;

import com.ceiroa.ihealth.model.daos.ClientDAO;
import com.ceiroa.ihealth.model.daos.VisitsDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DeleteClient {

	private static ClientDAO clientDAO;
	private static VisitsDAO visitsDAO;
	
	private String viewName;
	
	public void setViewName(String viewName) {
		this.viewName = viewName;
	}
	
	@Autowired
	public void setClientDAO(ClientDAO clientDAO) {
		DeleteClient.clientDAO = clientDAO;
	}

	@Autowired
	public void setVisitsDAO(VisitsDAO visitsDAO) {
		DeleteClient.visitsDAO = visitsDAO;
	}


	@RequestMapping("/deleteClient/delete.htm")
    public ModelAndView delete(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
		request.setAttribute("active", "manageClients");

        String clientId = request.getParameter("id");

        boolean clientHasVisits = visitsDAO.thereAreVisitsForClient(clientId);
        
        //There are visits associated with this client-cannot delete
        if(clientHasVisits) {
            request.setAttribute("errorMessage", "Visits exist for this client. It cannot be deleted."
                    + " If you still want to delete it, delete all his/her visits first.");
        } else {
            int success = clientDAO.deleteById(clientId);
            if(success==1) {
                    request.setAttribute("successMessage", "Client successfully deleted");
            } else {
                    request.setAttribute("errorMessage", "Couldn't delete client. Please try"
                        + " again later or contact support.");
            }
        }
        
        return new ModelAndView(viewName, null);
    }
}
