package com.ceiroa.ihealth.controller.forms;

import com.ceiroa.ihealth.model.DateHelper;
import com.ceiroa.ihealth.model.IntHelper;
import com.ceiroa.ihealth.model.PropertiesHandler;
import com.ceiroa.ihealth.model.PropsHandlerFactory;
import com.ceiroa.ihealth.model.forms.LumbarSpineVisit;
import com.ceiroa.ihealth.model.forms.iVisit;
import com.ceiroa.ihealth.model.forms.daos.VisitHelperDAO;
import com.ceiroa.ihealth.model.forms.daos.LumbarSpineDAO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class VisitLumbarSpine {

	private static VisitHelperDAO visitHelperDAO;
	private static LumbarSpineDAO lumbarSpineDAO;

	private final static String tableName = "lumbarSpineVisits";
	private final static iVisit instance = new LumbarSpineVisit();

	protected String viewName;

	@Autowired
	public void setVisitHelperDAO(VisitHelperDAO visitHelperDAO) {
		VisitLumbarSpine.visitHelperDAO = visitHelperDAO;
	}

	@Autowired
	public void setLumbarSpineDAO(LumbarSpineDAO lumbarSpineDAO) {
		VisitLumbarSpine.lumbarSpineDAO = lumbarSpineDAO;
	}

	public void setViewName(String viewName) {
		this.viewName = viewName;
	}

	@RequestMapping("/lumbarSpine/view.htm")
	public ModelAndView view(HttpServletRequest request,
			HttpServletResponse response) {
		request.setAttribute("active", "newVisit");
		return new ModelAndView(viewName, null);
	}

	@RequestMapping("/lumbarSpine/visit.htm")
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
			iVisit visit = new LumbarSpineVisit();
			VisitHelper.setCommonFields(visit, request);
			// Set lumbarSpine-specific fields
			setVisitFields(visit, request);

			int success = lumbarSpineDAO.insert(visit, tableName);
			if (success == 1) {
				request.setAttribute("successMessage", newVisitSuccessMessage);
			} else {
				request.setAttribute("errorMessage", newVisitErrorMessage);
			}
			return new ModelAndView("new/newVisit", null);
			// View existing visit
		} else if (postReqSource.equals("visitsLog") && !visitId.isEmpty()) {
			iVisit visit = visitHelperDAO.getVisitByID(lumbarSpineDAO, visitId,
					tableName, instance);
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
				iVisit visit = new LumbarSpineVisit();
				visit.setId(Long.parseLong(visitId));
				visit.setClientId(Long.parseLong(clientId));
				VisitHelper.setCommonFields(visit, request);
				VisitHelper.setDateCreated(visit, request);
				setVisitFields(visit, request);

				int message = lumbarSpineDAO.update(visit,
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
			iVisit visit = visitHelperDAO.getVisitByClientID(lumbarSpineDAO,
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
		((LumbarSpineVisit) visit).setFlex(IntHelper.parseInt(request
				.getParameter("flex")));
		((LumbarSpineVisit) visit).setExt(IntHelper.parseInt(request
				.getParameter("ext")));
		((LumbarSpineVisit) visit).setLlf(IntHelper.parseInt(request
				.getParameter("llf")));
		((LumbarSpineVisit) visit).setRlf(IntHelper.parseInt(request
				.getParameter("rlf")));
		((LumbarSpineVisit) visit).setLlr(IntHelper.parseInt(request
				.getParameter("llr")));
		((LumbarSpineVisit) visit).setRlr(IntHelper.parseInt(request
				.getParameter("rlr")));
		((LumbarSpineVisit) visit)
				.setValsavas(request.getParameter("valsavas"));
		((LumbarSpineVisit) visit).setStraightLeg(request
				.getParameter("straightLeg"));
		((LumbarSpineVisit) visit).setBrowstringTest(request
				.getParameter("browstringTest"));
		((LumbarSpineVisit) visit).setLasegueTest(request
				.getParameter("lasegueTest"));
		((LumbarSpineVisit) visit).setElyTest(request.getParameter("elyTest"));
		((LumbarSpineVisit) visit).setThomasTest(request
				.getParameter("thomasTest"));
		((LumbarSpineVisit) visit).setSpringTest(request
				.getParameter("springTest"));
		((LumbarSpineVisit) visit).setTrenderlenburgTest(request
				.getParameter("trenderlenburgTest"));
		((LumbarSpineVisit) visit).setBilateralLegRaise(request
				.getParameter("bilateralLegRaise"));
		((LumbarSpineVisit) visit).setPelvicRock(request
				.getParameter("pelvicRock"));
		((LumbarSpineVisit) visit).setPatrickFabere(request
				.getParameter("patrickFabere"));
		((LumbarSpineVisit) visit).setMilgram(request.getParameter("milgram"));
		((LumbarSpineVisit) visit).setMedLegFoot(request
				.getParameter("medLegFoot"));
		((LumbarSpineVisit) visit).setLatLeg(request.getParameter("latLeg"));
		((LumbarSpineVisit) visit).setLatFoot(request.getParameter("latFoot"));
		((LumbarSpineVisit) visit)
				.setPatellar(request.getParameter("patellar"));
		((LumbarSpineVisit) visit).setHamstring(request
				.getParameter("hamstring"));
		((LumbarSpineVisit) visit)
				.setAchilles(request.getParameter("achilles"));
		((LumbarSpineVisit) visit).setAntTibialis(request
				.getParameter("antTibialis"));
		((LumbarSpineVisit) visit).setExtHallucis(request
				.getParameter("extHallucis"));
		((LumbarSpineVisit) visit)
				.setPeroneus(request.getParameter("peroneus"));
	}

}
