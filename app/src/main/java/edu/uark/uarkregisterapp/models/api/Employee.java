package edu.uark.uarkregisterapp.models.api;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

//import edu.uark.uarkregisterapp.models.api.fields.ProductFieldName; //Do we need an Employee version of this?
import edu.uark.uarkregisterapp.models.api.interfaces.ConvertToJsonInterface;
import edu.uark.uarkregisterapp.models.api.interfaces.LoadFromJsonInterface;
import edu.uark.uarkregisterapp.models.transition.EmployeeTransition;

public class Employee implements ConvertToJsonInterface, LoadFromJsonInterface<Employee> {
	/*List of variables present:
		UUID record_id
		String first_name
		String last_name
		int employee_id
		String active
		String title
		int manager
		String password
	*/	

	private UUID record_id;
	public UUID getId() {
		return this.record_id;
	}

	public Employee setId(UUID record_id) {
		this.record_id = record_id;
		return this;
	}


	private String first_name;
	public String getFirst_Name() {
		return this.first_name;
	}

	public Employee setFirst_Name(String first_name) {
		this.first_name = first_name;
		return this;
	}


	private String last_name;
	public String getLast_Name() {
		return this.last_name;
	}

	public Employee setLast_Name(String last_name) {
		this.last_name = last_name;
		return this;
	}


	private String employee_id;
	public String getEmployee_Id() {
		return this.employee_id;
	}

	public Employee setEmployee_Id(String employee_id) {
		this.employee_id = employee_id;
		return this;
	}


	private String active;
	public String getActive() {
		return this.active;
	}

	public Employee setActive(String active) {
		this.active = active;
		return this;
	}


	private String title;
	public String getTitle() {
		return this.title;
	}

	public Employee setTitle(String title) {
		this.title = title;
		return this;
	}


	private String manager;
	public String getManager() {
		return this.manager;
	}

	public Employee setManager(String manager) {
		this.manager = manager;
		return this;
	}


	private String password;
	public String getPassword() {
		return this.password;
	}
	
	public Employee setPassword(String password) {
		this.password = password;
		return this;
	}

	private Date createdOn;
	public Date getCreatedOn() {
		return this.createdOn;
	}

	public Employee setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
		return this;
	}


	//TODO: Figure out what to do about EmployeeFieldName EmployeeListingFieldName
	//	Because this methods need those.
	/*
	@Override
	public Product loadFromJson(JSONObject rawJsonObject) {
		String value = rawJsonObject.optString(ProductFieldName.ID.getFieldName());
		if (!StringUtils.isBlank(value)) {
			this.id = UUID.fromString(value);
		}

		this.lookupCode = rawJsonObject.optString(ProductFieldName.LOOKUP_CODE.getFieldName());
		this.count = rawJsonObject.optInt(ProductFieldName.COUNT.getFieldName());

		value = rawJsonObject.optString(ProductFieldName.CREATED_ON.getFieldName());
		if (!StringUtils.isBlank(value)) {
			try {
				this.createdOn = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.US).parse(value);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		return this;
	}

	@Override
	public JSONObject convertToJson() {
		JSONObject jsonObject = new JSONObject();

		try {
			jsonObject.put(ProductFieldName.ID.getFieldName(), this.id.toString());
			jsonObject.put(ProductFieldName.LOOKUP_CODE.getFieldName(), this.lookupCode);
			jsonObject.put(ProductFieldName.COUNT.getFieldName(), this.count);
			jsonObject.put(ProductFieldName.CREATED_ON.getFieldName(), (new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.US)).format(this.createdOn));
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return jsonObject;
	}
	*/

	public Employee() {
		this.record_id = new UUID(0, 0);
		this.first_name = "";
		this.last_name = "";
		this.employee_id = "";
		this.active = "";
		this.title = "";
		this.manager = "";
		this.password = "";
		this.createdOn = new Date();
	}

	public Employee(EmployeeTransition employeeTransition) {
		this.record_id = employeeTransition.getId();
		this.first_name = employeeTransition.getFirst_Name()
		this.last_name = employeeTransition.getLast_Name();
		this.employee_id = employeeTransition.getEmployee_Id();
		this.active = employeeTransition.getActive();
		this.title = employeeTransition.getTitle();
		this.manager = employeeTransition.getManager();
		this.password = employeeTransition.getPassword();
		this.createdOn = employeeTransition.getCreatedOn();
	}
}































//
