package com.ceiroa.ihealth.model.forms;

public interface iTopVisit {

	public long getId();
    public long getClientId();
    public String getDateCreated();
    public String getDateUpdated();
    public String getFirstName();
    public String getLastName();
    public String getController();

    public void setId(long id);
    public void setClientId(long clientId);
    public void setDateCreated(String dateCreated);
    public void setDateUpdated(String dateUpdated);
    public void setFirstName(String firstName);
    public void setLastName(String lastName);
    public void setController(String controller);
}
