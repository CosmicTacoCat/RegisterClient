package edu.uark.uarkregisterapp.models.api.fields;

import edu.uark.uarkregisterapp.models.api.interfaces.FieldNameInterface;

public enum EmployeeFieldName implements FieldNameInterface {
	RECORD_ID("record_id"),
	FIRST_NAME("first_name"),
	LAST_NAME("last_name"),
	EMPLOYEE_ID("employee_id"),
	ACTIVE("active"),
	TITLE("title"),
	MANAGER("manager"),
	PASSWORD("password"),
	CREATED("created"),
	API_REQUEST_STATUS("apiRequestStatus"),
	API_REQUEST_MESSAGE("apiRequestMessage");

	private String fieldName;
	public String getFieldName() {
		return this.fieldName;
	}

	EmployeeFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
}
