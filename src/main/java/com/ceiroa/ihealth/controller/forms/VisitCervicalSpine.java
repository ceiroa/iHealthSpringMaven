package com.ceiroa.ihealth.controller.forms;

import com.ceiroa.ihealth.model.forms.CervicalSpineVisit;
import com.ceiroa.ihealth.model.DateHelper;
import com.ceiroa.ihealth.model.IntHelper;
import com.ceiroa.ihealth.model.PropertiesHandler;
import com.ceiroa.ihealth.model.PropsHandlerFactory;
import com.ceiroa.ihealth.model.forms.iVisit;
import com.ceiroa.ihealth.model.forms.daos.VisitHelperDAO;
import com.ceiroa.ihealth.model.forms.daos.CervicalSpineDAO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class VisitCervicalSpine {

	private static CervicalSpineDAO cervicalSpineDAO;
	private static VisitHelperDAO visitHelperDAO;

	private final static String tableName = "cervicalSpineVisits";
	private final static iVisit instance = new CervicalSpineVisit();

	protected String viewName;

	@Autowired
	public void setCervicalSpineDAO(CervicalSpineDAO cervicalSpineDAO) {
		VisitCervicalSpine.cervicalSpineDAO = cervicalSpineDAO;
	}

	@Autowired
	public void setVisitHelperDAO(VisitHelperDAO visitHelperDAO) {
		VisitCervicalSpine.visitHelperDAO = visitHelperDAO;
	}

	public void setViewName(String viewName) {
		this.viewName = viewName;
	}

	@RequestMapping("/cervicalSpine/view.htm")
	public ModelAndView view(HttpServletRequest request,
			HttpServletResponse response) {
		request.setAttribute("active", viewName);
		return new ModelAndView(viewName, null);
	}

	@RequestMapping("/cervicalSpine/visit.htm")
	public ModelAndView visit(HttpServletRequest request,
			HttpServletResponse response) {
		request.setAttribute("active", "newVisit");

		String visitId = request.getParameter("visitId");
		String clientId = request.getParameter("clientId");
		String postReqSource = request.getParameter("postReqSource");
		String noMenu = request.getParameter("noMenu");

		request.setAttribute("visitId", visitId);
		request.setAttribute("clientId", clientId);
		request.setAttribute("noMenu", noMenu);

		PropertiesHandler handler = PropsHandlerFactory.getHandler();
		String newVisitSuccessMessage = handler
				.getProperty("message.newVisitSuccess");
		String newVisitErrorMessage = handler
				.getProperty("message.newVisitError");
		String updateVisitSuccessMessage = handler
				.getProperty("message.updateVisitSuccess");
		String updateVisitErrorMessage = handler
				.getProperty("message.updateVisitError");
		String enterValidDateMessage = handler
				.getProperty("message.enterValidDate");

		// Save new visit - The user already filled the information
		if (postReqSource.equals("entryForm") && !clientId.isEmpty()
				&& visitId.isEmpty()) {
			iVisit visit = new CervicalSpineVisit();
			VisitHelper.setCommonFields(visit, request);
			VisitHelper.setDateCreated(visit, request);
			// Set cervicalSpine-specific fields
			setVisitFields(visit, request);

			int success = cervicalSpineDAO.insert(visit, tableName);
			if (success == 1) {
				request.setAttribute("successMessage", newVisitSuccessMessage);
			} else {
				request.setAttribute("errorMessage", newVisitErrorMessage);
			}
			return new ModelAndView("new/newVisit", null);
			// View existing visit
		} else if (postReqSource.equals("visitsLog") && !visitId.isEmpty()) {
			iVisit visit = visitHelperDAO.getVisitByID(cervicalSpineDAO,
					visitId, tableName, instance);
			request.setAttribute("visit", visit);
			String dateCreated = visit.getDateCreated();
			if (dateCreated != null) {
				request.setAttribute("date", dateCreated);
			} else {
				request.setAttribute("date", DateHelper.now());
			}
			return new ModelAndView(viewName, null);
			// Update existing visit
		} else if (postReqSource.equals("entryForm") && !visitId.isEmpty()) {
			String date = request.getParameter("dateCreated");
			if (!date.isEmpty()) {
				iVisit visit = new CervicalSpineVisit();
				visit.setId(Long.parseLong(visitId));
				visit.setClientId(Long.parseLong(clientId));
				VisitHelper.setCommonFields(visit, request);
				VisitHelper.setDateCreated(visit, request);
				setVisitFields(visit, request);

				int message = cervicalSpineDAO.update(visit,
						Integer.parseInt(visitId), tableName);

				if (message != 0) {
					visitHelperDAO.updateDateCreated(visit,
							Integer.parseInt(visitId), tableName);
					request.setAttribute("successMessage",
							updateVisitSuccessMessage);
				} else {
					request.setAttribute("errorMessage",
							updateVisitErrorMessage);
				}
			} else {
				request.setAttribute("errorMessage", enterValidDateMessage);
			}
			request.setAttribute("active", "visits");
			return new ModelAndView("manage/manageVisits", null);
			// The user wants to create a new visit. We check first if
			// a visit of this type exists for this client
		} else {
			iVisit visit = visitHelperDAO.getVisitByClientID(cervicalSpineDAO,
					clientId, tableName, instance);
			if (visit != null) {
				request.setAttribute("visit", visit);
				request.setAttribute("date", visit.getDateCreated());
			} else {
				request.setAttribute("date", DateHelper.now());
			}
			return new ModelAndView(viewName, null);
		}
	}

	private void setVisitFields(iVisit visit, HttpServletRequest request) {
		((CervicalSpineVisit) visit).setFlex(IntHelper.parseInt(request
				.getParameter("flex")));
		((CervicalSpineVisit) visit).setLlf(IntHelper.parseInt(request
				.getParameter("llf")));
		((CervicalSpineVisit) visit).setLlr(IntHelper.parseInt(request
				.getParameter("llr")));
		((CervicalSpineVisit) visit).setExt(IntHelper.parseInt(request
				.getParameter("ext")));
		((CervicalSpineVisit) visit).setRlf(IntHelper.parseInt(request
				.getParameter("rlf")));
		((CervicalSpineVisit) visit).setRlr(IntHelper.parseInt(request
				.getParameter("rlr")));
		((CervicalSpineVisit) visit).setJacksonComp(request
				.getParameter("jacksonComp"));
		((CervicalSpineVisit) visit)
				.setMaxComp(request.getParameter("maxComp"));
		((CervicalSpineVisit) visit).setShoulderDep(request
				.getParameter("shoulderDep"));
		((CervicalSpineVisit) visit).setSotoHall(request
				.getParameter("sotoHall"));
		((CervicalSpineVisit) visit).setSpurlings(request
				.getParameter("spurlings"));
		((CervicalSpineVisit) visit).setCsDistraction(request
				.getParameter("csDistraction"));
		((CervicalSpineVisit) visit).setValsavas(request
				.getParameter("valsavas"));
		((CervicalSpineVisit) visit)
				.setBaccody(request.getParameter("baccody"));
		((CervicalSpineVisit) visit).setLatArm(request.getParameter("latArm"));
		((CervicalSpineVisit) visit).setLatForearm(request
				.getParameter("latForearm"));
		((CervicalSpineVisit) visit).setMiddleFinger(request
				.getParameter("middleFinger"));
		((CervicalSpineVisit) visit).setMedForearm(request
				.getParameter("medForearm"));
		((CervicalSpineVisit) visit).setMedArm(request.getParameter("medArm"));
		((CervicalSpineVisit) visit).setBiceps(request.getParameter("biceps"));
		((CervicalSpineVisit) visit).setBrachiorad(request
				.getParameter("brachiorad"));
		((CervicalSpineVisit) visit)
				.setTriceps(request.getParameter("triceps"));
		((CervicalSpineVisit) visit).setShoulderAbd(request
				.getParameter("shoulderAbd"));
		((CervicalSpineVisit) visit).setWristExt(request
				.getParameter("wristExt"));
		((CervicalSpineVisit) visit).setWristFlex(request
				.getParameter("wristFlex"));
		((CervicalSpineVisit) visit).setFingerExt(request
				.getParameter("fingerExt"));
		((CervicalSpineVisit) visit).setFingerFlex(request
				.getParameter("fingerFlex"));
		((CervicalSpineVisit) visit).setFingerAbd(request
				.getParameter("fingerAbd"));
	}
}
