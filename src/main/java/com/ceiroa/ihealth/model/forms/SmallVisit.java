package com.ceiroa.ihealth.model.forms;

public class SmallVisit implements iTopVisit {

    private long id;
    private long clientId;
    private String firstName; //From client table
    private String lastName;  //From client table
    private String dateCreated;
    private String dateUpdated;
    private String controller;
    
	public long getId() {
		return id;
	}

	public long getClientId() {
		return clientId;
	}

	public String getDateCreated() {
		return dateCreated;
	}

	public String getDateUpdated() {
		return dateUpdated;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getController() {
		return controller;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setClientId(long clientId) {
		this.clientId = clientId;
	}

	public void setDateCreated(String dateCreated) {
		this.dateCreated = dateCreated;
	}

	public void setDateUpdated(String dateUpdated) {
		this.dateUpdated = dateUpdated;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setController(String controller) {
		this.controller = controller;
	}

}
