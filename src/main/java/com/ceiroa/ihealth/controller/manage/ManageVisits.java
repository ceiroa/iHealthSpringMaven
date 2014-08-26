package com.ceiroa.ihealth.controller.manage;

import com.ceiroa.ihealth.model.DateHelper;
import com.ceiroa.ihealth.model.daos.VisitsDAO;
import com.ceiroa.ihealth.model.forms.iTopVisit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ManageVisits {

	private static VisitsDAO visitsDAO;
	private String viewName;
	
	public void setViewName(String viewName) {
		this.viewName = viewName;
	}
	
	@Autowired
	public void setVisitsDAO(VisitsDAO visitsDAO) {
		ManageVisits.visitsDAO = visitsDAO;
	}

	@RequestMapping("manageVisits/view.htm")
    public ModelAndView view(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("active", "visits");
        request.setAttribute("infoMessage", "Search for visits to view, edit, or delete:    ");
        
        return new ModelAndView(viewName, null);
    } 

	@RequestMapping("manageVisits/find.htm")
    public ModelAndView find(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("active", "visits");

        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String date = request.getParameter("date");

        boolean dateFormatCorrect = true;

        ArrayList<iTopVisit> visits = null;
        
        if(!date.equals("") && DateHelper.isCorrectFormat(date)){
            request.setAttribute("errorMessage", "Please enter date in format yyyy-MM-dd");
            dateFormatCorrect = false;
        } else {
            Map<String, String> constraints = new HashMap<String, String>();

            if(date!=null && !date.equals("") && dateFormatCorrect)
                constraints.put("date", date);
            if(firstName!=null && !firstName.equals(""))
                constraints.put("firstName", firstName);
            if(lastName!=null && !lastName.equals(""))
                constraints.put("lastName", lastName);

            visits = visitsDAO.getAllVisitsMultipleConstraints(constraints);
        }

        request.setAttribute("visits", visits);

        if((visits==null || visits.isEmpty()) && dateFormatCorrect) {
            request.setAttribute("errorMessage", "No visits found");
        }
        
        return new ModelAndView(viewName, null);
    }
}
