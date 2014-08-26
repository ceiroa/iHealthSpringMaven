package com.ceiroa.ihealth.model.forms.daos;

import java.util.Map;

import com.ceiroa.ihealth.model.forms.iVisit;

public interface iVisitDAO {

	public void setVisitProperties(Map<String, Object> result, iVisit visit);

}
