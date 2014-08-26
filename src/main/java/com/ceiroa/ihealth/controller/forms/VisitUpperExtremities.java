package com.ceiroa.ihealth.controller.forms;

import com.ceiroa.ihealth.model.DateHelper;
import com.ceiroa.ihealth.model.IntHelper;
import com.ceiroa.ihealth.model.PropertiesHandler;
import com.ceiroa.ihealth.model.PropsHandlerFactory;
import com.ceiroa.ihealth.model.forms.UpperExtremitiesVisit;
import com.ceiroa.ihealth.model.forms.iVisit;
import com.ceiroa.ihealth.model.forms.daos.VisitHelperDAO;
import com.ceiroa.ihealth.model.forms.daos.UpperExtremitiesDAO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class VisitUpperExtremities {

	private static VisitHelperDAO visitHelperDAO;
	private static UpperExtremitiesDAO upperExtremitiesDAO;

	private static final String tableName = "upperExtremitiesVisits";
	private static final iVisit instance = new UpperExtremitiesVisit();

	protected String viewName;

	@Autowired
	public void setVisitHelperDAO(VisitHelperDAO visitHelperDAO) {
		VisitUpperExtremities.visitHelperDAO = visitHelperDAO;
	}

	@Autowired
	public void setUpperExtremitiesDAO(UpperExtremitiesDAO upperExtremitiesDAO) {
		VisitUpperExtremities.upperExtremitiesDAO = upperExtremitiesDAO;
	}

	public void setViewName(String viewName) {
		this.viewName = viewName;
	}

	@RequestMapping("/upperExtremities/view.htm")
	public ModelAndView view(HttpServletRequest request,
			HttpServletResponse response) {
		request.setAttribute("active", "newVisit");
		return new ModelAndView(viewName, null);
	}

	@RequestMapping("/upperExtremities/visit.htm")
	protected ModelAndView visit(HttpServletRequest request,
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
			iVisit visit = new UpperExtremitiesVisit();
			VisitHelper.setCommonFields(visit, request);
			// Set uppesExtremities-specific fields
			setVisitFields(visit, request);

			int success = upperExtremitiesDAO.insert(visit, tableName);
			if (success == 1) {
				request.setAttribute("successMessage", newVisitSuccessMessage);
			} else {
				request.setAttribute("errorMessage", newVisitErrorMessage);
			}
			return new ModelAndView("new/newVisit", null);
			// View existing visit
		} else if (postReqSource.equals("visitsLog") && !visitId.isEmpty()) {
			iVisit visit = visitHelperDAO.getVisitByID(upperExtremitiesDAO,
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
				iVisit visit = new UpperExtremitiesVisit();
				visit.setId(Long.parseLong(visitId));
				visit.setClientId(Long.parseLong(clientId));
				VisitHelper.setCommonFields(visit, request);
				VisitHelper.setDateCreated(visit, request);
				setVisitFields(visit, request);

				int message = upperExtremitiesDAO.update(visit,
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
					upperExtremitiesDAO, clientId, tableName, instance);
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
		((UpperExtremitiesVisit) visit).setShoulderFlex(IntHelper
				.parseInt(request.getParameter("shoulderFlex")));
		((UpperExtremitiesVisit) visit).setShoulderLr(IntHelper
				.parseInt(request.getParameter("shoulderLr")));
		((UpperExtremitiesVisit) visit).setShoulderAbd(IntHelper
				.parseInt(request.getParameter("shoulderAbd")));
		((UpperExtremitiesVisit) visit).setShoulderExt(IntHelper
				.parseInt(request.getParameter("shoulderExt")));
		((UpperExtremitiesVisit) visit).setShoulderMr(IntHelper
				.parseInt(request.getParameter("shoulderMr")));
		((UpperExtremitiesVisit) visit).setShoulderAdd(IntHelper
				.parseInt(request.getParameter("shoulderAdd")));
		((UpperExtremitiesVisit) visit).setElbowFlex(IntHelper.parseInt(request
				.getParameter("elbowFlex")));
		((UpperExtremitiesVisit) visit).setElbowPro(IntHelper.parseInt(request
				.getParameter("elbowPro")));
		((UpperExtremitiesVisit) visit).setElbowExt(IntHelper.parseInt(request
				.getParameter("elbowExt")));
		((UpperExtremitiesVisit) visit).setElbowSup(IntHelper.parseInt(request
				.getParameter("elbowSup")));
		((UpperExtremitiesVisit) visit).setWristFlex(IntHelper.parseInt(request
				.getParameter("wristFlex")));
		((UpperExtremitiesVisit) visit).setWristAbd(IntHelper.parseInt(request
				.getParameter("wristAbd")));
		((UpperExtremitiesVisit) visit).setWristExt(IntHelper.parseInt(request
				.getParameter("wristExt")));
		((UpperExtremitiesVisit) visit).setWristAdd(IntHelper.parseInt(request
				.getParameter("wristAdd")));
		((UpperExtremitiesVisit) visit).setDropArmTest(request
				.getParameter("dropArmTest"));
		((UpperExtremitiesVisit) visit).setDrawbarnTest(request
				.getParameter("drawbarnTest"));
		((UpperExtremitiesVisit) visit).setSupraspinatusTest(request
				.getParameter("supraspinatusTest"));
		((UpperExtremitiesVisit) visit).setApleyScratchTest(request
				.getParameter("apleyScratchTest"));
		((UpperExtremitiesVisit) visit).setPostImpingSign(request
				.getParameter("postImpingSign"));
		((UpperExtremitiesVisit) visit).setSpeedTest(request
				.getParameter("speedTest"));
		((UpperExtremitiesVisit) visit).setCrossOverImpTest(request
				.getParameter("crossOverImpTest"));
		((UpperExtremitiesVisit) visit).setYergasonTest(request
				.getParameter("yergasonTest"));
		((UpperExtremitiesVisit) visit).setApprehensionTest(request
				.getParameter("apprehensionTest"));
		((UpperExtremitiesVisit) visit).setDrawerTest(request
				.getParameter("drawerTest"));
		((UpperExtremitiesVisit) visit).setVarusStressTest(request
				.getParameter("varusStressTest"));
		((UpperExtremitiesVisit) visit).setCozensTest(request
				.getParameter("cozensTest"));
		((UpperExtremitiesVisit) visit).setValgusStressTest(request
				.getParameter("valgusStressTest"));
		((UpperExtremitiesVisit) visit).setGolferElbow(request
				.getParameter("golferElbow"));
		((UpperExtremitiesVisit) visit).setTinelSign(request
				.getParameter("tinelSign"));
		((UpperExtremitiesVisit) visit).setPinchGripTest(request
				.getParameter("pinchGripTest"));
		((UpperExtremitiesVisit) visit).setFromentTest(request
				.getParameter("fromentTest"));
		((UpperExtremitiesVisit) visit).setPhalenTest(request
				.getParameter("phalenTest"));
		((UpperExtremitiesVisit) visit).setFingerTapTest(request
				.getParameter("fingerTapTest"));
		((UpperExtremitiesVisit) visit).setFinkelsteninTest(request
				.getParameter("finkelsteninTest"));
		((UpperExtremitiesVisit) visit).setBunnelLitter(request
				.getParameter("bunnelLitter"));
	}
}