package com.ceiroa.ihealth.controller.newitem;

import com.ceiroa.ihealth.model.Client;
import com.ceiroa.ihealth.model.daos.ClientDAO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class NewClient {

	private static ClientDAO clientDAO;

	private String viewName;
	private String messageViewName;

	public void setViewName(String viewName) {
		this.viewName = viewName;
	}

	public void setMessageViewName(String messageViewName) {
		this.messageViewName = messageViewName;
	}

	@Autowired
	public void setClientDAO(ClientDAO clientDAO) {
		NewClient.clientDAO = clientDAO;
	}

	@RequestMapping("newClient/view.htm")
	public ModelAndView view(HttpServletRequest request,
			HttpServletResponse response) {
		request.setAttribute("active", "newClient");
		return new ModelAndView(viewName, null);
	}

	@RequestMapping("newClient/viewClient.htm")
	public ModelAndView viewClient(HttpServletRequest request,
			HttpServletResponse response) {
		request.setAttribute("active", "newClient");

		String id = request.getParameter("id");

		Client client = clientDAO.findClientByID(id);
		request.setAttribute("client", client);

		return new ModelAndView(viewName, null);
	}

	@RequestMapping("newClient/save.htm")
	public ModelAndView update(HttpServletRequest request,
			HttpServletResponse response) {
		request.setAttribute("active", "newClient");

		String id = request.getParameter("id");
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");

		// Update-We update and already existing client
		if (!id.isEmpty()) {
			Client client = new Client();
			client.setId(Long.parseLong(request.getParameter("id")));
			setClientFields(client, request);

			int message = 0;
			try {
				message = clientDAO.update(client);
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (message != 0) {
				request.setAttribute("successMessage",
						"Client successfully updated");
			} else {
				request.setAttribute("errorMessage", "Error. Client could not "
						+ "be updated. Please try again or contact support.");
			}

			request.setAttribute("active", "manageClients");
			return new ModelAndView(messageViewName, null);
		} else {
			if (!firstName.isEmpty() && !lastName.isEmpty()) {
				Client client = new Client();
				setClientFields(client, request);

				int message = clientDAO.insert(client);
				if (message != 0) {
					request.setAttribute("active", "manageClients");
					request.setAttribute("successMessage",
							"Client successfully created");
					return new ModelAndView("manage/manageClients", null);
				} else {
					request.setAttribute(
							"errorMessage",
							"Error. Client could not "
									+ "be created. Please try again or contact support.");
					return new ModelAndView(viewName, null);
				}
			} else {
				request.setAttribute("errorMessage", "Please fill at least the"
						+ " first and last name fields.");
				return new ModelAndView(viewName, null);
			}
		}
	}

	private void setClientFields(Client client, HttpServletRequest request) {
		client.setFirstName(request.getParameter("firstName"));
		client.setMiddleInitial(request.getParameter("middleInitial"));
		client.setLastName(request.getParameter("lastName"));
		client.setGender(request.getParameter("gender"));
		client.setAddress(request.getParameter("address"));
		client.setCity(request.getParameter("city"));
		client.setState(request.getParameter("usState"));
		client.setZipcode(request.getParameter("zipcode"));
		client.setEmail(request.getParameter("email"));
		client.setReferrer(request.getParameter("referrer"));
		client.setHomePhone(request.getParameter("homePhone"));
		client.setCellPhone(request.getParameter("cellPhone"));
		client.setWorkPhone(request.getParameter("workPhone"));
		client.setDob(request.getParameter("dob"));
		client.setSsn(request.getParameter("ssn"));
		client.setDriverLicense(request.getParameter("driverLicense"));
		client.setEmployer(request.getParameter("employer"));
		client.setOccupation(request.getParameter("occupation"));
		client.setEmployerAddress(request.getParameter("employerAddress"));
		client.setEmployerPhoneNum(request.getParameter("employerPhoneNum"));
		client.setContactName(request.getParameter("contactName"));
		client.setContactRelation(request.getParameter("contactRelation"));
		client.setContactPhone(request.getParameter("contactPhone"));
		client.setInsurance(request.getParameter("insurance"));
		client.setInsuranceAddress(request.getParameter("insuranceAddress"));
		client.setPolicyHolderName(request.getParameter("policyHolderName"));
		client.setPolicyHolderAddress(request
				.getParameter("policyHolderAddress"));
		client.setPolicyHolderDob(request.getParameter("policyHolderDob"));
		client.setPolicyHolderSsn(request.getParameter("policyHolderSsn"));
		client.setPolicyNumber(request.getParameter("policyNumber"));
		client.setGroupNumber(request.getParameter("groupNumber"));
		client.setPolicyHolderRelation(request
				.getParameter("policyHolderRelation"));
		client.setInsurance2(request.getParameter("insurance2"));
		client.setInsuranceAddress2(request.getParameter("insuranceAddress2"));
		client.setPolicyHolderName2(request.getParameter("policyHolderName2"));
		client.setPolicyHolderAddress2(request
				.getParameter("policyHolderAddress2"));
		client.setPolicyHolderDob2(request.getParameter("policyHolderDob2"));
		client.setPolicyHolderSsn2(request.getParameter("policyHolderSsn2"));
		client.setPolicyNumber2(request.getParameter("policyNumber2"));
		client.setGroupNumber2(request.getParameter("groupNumber2"));
		client.setPolicyHolderRelation2(request
				.getParameter("policyHolderRelation2"));
		client.setAccidentInfo(request.getParameter("accidentInfo"));
		client.setCompInfo(request.getParameter("compInfo"));
	}
}
