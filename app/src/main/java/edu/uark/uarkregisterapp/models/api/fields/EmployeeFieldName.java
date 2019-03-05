package edu.uark.uarkregisterapp.models.api.fields;

import edu.uark.uarkregisterapp.models.api.interfaces.FieldNameInterface;

public enum EmployeeFieldName implements FieldNameInterface {
	ID("id"),
	FIRST_NAME("first_Name"),
	LAST_NAME("last_Name"),
	EMPLOYEE_ID("employee_Id"),
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
