package edu.uark.uarkregisterapp.models.api.fields;

import edu.uark.uarkregisterapp.models.api.interfaces.FieldNameInterface;

public enum CartFieldName implements FieldNameInterface {
	LOOKUP_CODE("lookupCode"),
	COUNT("count");

	private String fieldName;
	public String getFieldName() {
		return this.fieldName;
	}

	CartFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
}
