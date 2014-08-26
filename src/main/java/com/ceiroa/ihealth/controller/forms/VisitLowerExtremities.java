package com.ceiroa.ihealth.controller.forms;

import com.ceiroa.ihealth.model.DateHelper;
import com.ceiroa.ihealth.model.IntHelper;
import com.ceiroa.ihealth.model.PropertiesHandler;
import com.ceiroa.ihealth.model.PropsHandlerFactory;
import com.ceiroa.ihealth.model.forms.LowerExtremitiesVisit;
import com.ceiroa.ihealth.model.forms.iVisit;
import com.ceiroa.ihealth.model.forms.daos.VisitHelperDAO;
import com.ceiroa.ihealth.model.forms.daos.LowerExtremitiesDAO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class VisitLowerExtremities {

	private static LowerExtremitiesDAO lowerExtremitiesDAO;
	private static VisitHelperDAO visitHelperDAO;

	private final static String tableName = "lowerExtremitiesVisits";
	private final static iVisit instance = new LowerExtremitiesVisit();

	protected String viewName;

	@Autowired
	public void setLowerExtremitiesDAO(LowerExtremitiesDAO lowerExtremitiesDAO) {
		VisitLowerExtremities.lowerExtremitiesDAO = lowerExtremitiesDAO;
	}

	@Autowired
	public void setVisitHelperDAO(VisitHelperDAO visitHelperDAO) {
		VisitLowerExtremities.visitHelperDAO = visitHelperDAO;
	}

	public void setViewName(String viewName) {
		this.viewName = viewName;
	}

	@RequestMapping("/lowerExtremities/view.htm")
	public ModelAndView view(HttpServletRequest request,
			HttpServletResponse response) {
		request.setAttribute("active", "newVisit");
		return new ModelAndView(viewName, null);
	}

	@RequestMapping("/lowerExtremities/visit.htm")
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
			iVisit visit = new LowerExtremitiesVisit();
			VisitHelper.setCommonFields(visit, request);
			// Set lowerExtremities-specific fields
			setVisitFields(visit, request);

			int success = lowerExtremitiesDAO.insert(visit, tableName);
			if (success == 1) {
				request.setAttribute("successMessage", newVisitSuccessMessage);
			} else {
				request.setAttribute("errorMessage", newVisitErrorMessage);
			}
			return new ModelAndView("new/newVisit", null);
			// View existing visit
		} else if (postReqSource.equals("visitsLog") && !visitId.isEmpty()) {
			iVisit visit = visitHelperDAO.getVisitByID(lowerExtremitiesDAO,
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
				iVisit visit = new LowerExtremitiesVisit();
				visit.setId(Long.parseLong(visitId));
				visit.setClientId(Long.parseLong(clientId));
				VisitHelper.setCommonFields(visit, request);
				VisitHelper.setDateCreated(visit, request);
				setVisitFields(visit, request);

				int message = lowerExtremitiesDAO.update(visit,
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
			iVisit visit = visitHelperDAO.getVisitByClientID(
					lowerExtremitiesDAO, clientId, tableName, instance);
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
		((LowerExtremitiesVisit) visit).setKneeFlex(IntHelper.parseInt(request
				.getParameter("kneeFlex")));
		((LowerExtremitiesVisit) visit).setKneeExt(IntHelper.parseInt(request
				.getParameter("kneeExt")));
		((LowerExtremitiesVisit) visit).setPf(IntHelper.parseInt(request
				.getParameter("pf")));
		((LowerExtremitiesVisit) visit).setDf(IntHelper.parseInt(request
				.getParameter("df")));
		((LowerExtremitiesVisit) visit).setInv(IntHelper.parseInt(request
				.getParameter("inv")));
		((LowerExtremitiesVisit) visit).setEv(IntHelper.parseInt(request
				.getParameter("ev")));
		((LowerExtremitiesVisit) visit).setHipFlex(IntHelper.parseInt(request
				.getParameter("hipFlex")));
		((LowerExtremitiesVisit) visit).setHipExt(IntHelper.parseInt(request
				.getParameter("hipExt")));
		((LowerExtremitiesVisit) visit).setHipAbd(IntHelper.parseInt(request
				.getParameter("hipAbd")));
		((LowerExtremitiesVisit) visit).setHipAdd(IntHelper.parseInt(request
				.getParameter("hipAdd")));
		((LowerExtremitiesVisit) visit).setHipLr(IntHelper.parseInt(request
				.getParameter("hipLr")));
		((LowerExtremitiesVisit) visit).setHipMr(IntHelper.parseInt(request
				.getParameter("hipMr")));
		((LowerExtremitiesVisit) visit).setHipHyp(IntHelper.parseInt(request
				.getParameter("hipHyp")));
		((LowerExtremitiesVisit) visit).setTrendelenbarg(request
				.getParameter("trendelenbarg"));
		((LowerExtremitiesVisit) visit).setLegLength(request
				.getParameter("legLength"));
		((LowerExtremitiesVisit) visit).setThomasTest(request
				.getParameter("thomasTest"));
		((LowerExtremitiesVisit) visit).setOberTest(request
				.getParameter("oberTest"));
		((LowerExtremitiesVisit) visit).setMcMurray(request
				.getParameter("mcMurray"));
		((LowerExtremitiesVisit) visit).setApleyTest(request
				.getParameter("apleyTest"));
		((LowerExtremitiesVisit) visit).setBounceHome(request
				.getParameter("bounceHome"));
		((LowerExtremitiesVisit) visit).setPatellaGrinding(request
				.getParameter("patellaGrinding"));
		((LowerExtremitiesVisit) visit).setApprehensionPatella(request
				.getParameter("apprehensionPatella"));
		((LowerExtremitiesVisit) visit).setTinelSign(request
				.getParameter("tinelSign"));
		((LowerExtremitiesVisit) visit).setEffusionTest(request
				.getParameter("effusionTest"));
		((LowerExtremitiesVisit) visit).setRigidFlatFeet(request
				.getParameter("rigidFlatFeet"));
		((LowerExtremitiesVisit) visit).setTibialTorsion(request
				.getParameter("tibialTorsion"));
		((LowerExtremitiesVisit) visit).setHomansSign(request
				.getParameter("homansSign"));
		((LowerExtremitiesVisit) visit).setForefootTest(request
				.getParameter("forefootTest"));
		((LowerExtremitiesVisit) visit).setAnkleDorsiflexion(request
				.getParameter("ankleDorsiflexion"));
	}

}
