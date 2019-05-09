package edu.uark.uarkregisterapp.models.api.enums;

import java.util.HashMap;
import java.util.Map;

import edu.uark.uarkregisterapp.models.api.interfaces.PathElementInterface;

public enum EmployeeApiMethod implements PathElementInterface {
	NONE(""),
	LOGIN("login"),
	CREATE("create"),
	BY_EMPLOYEE_ID("byemployee_id");

	@Override
	public String getPathValue() {
		return value;
	}

	private String value;

	EmployeeApiMethod(String value) {
		this.value = value;
	}
}
