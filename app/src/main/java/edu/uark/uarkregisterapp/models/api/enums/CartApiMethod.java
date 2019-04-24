package edu.uark.uarkregisterapp.models.api.enums;

import edu.uark.uarkregisterapp.models.api.interfaces.PathElementInterface;

public enum CartApiMethod implements PathElementInterface {
	NONE(""),
	BY_LOOKUP_CODE("bylookupcode");

	@Override
	public String getPathValue() {
		return value;
	}

	private String value;

	CartApiMethod(String value) {
		this.value = value;
	}
}
