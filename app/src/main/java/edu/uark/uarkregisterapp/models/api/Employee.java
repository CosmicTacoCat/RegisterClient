package edu.uark.uarkregisterapp.models.api;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

import edu.uark.uarkregisterapp.models.api.fields.EmployeeFieldName;
import edu.uark.uarkregisterapp.models.api.interfaces.ConvertToJsonInterface;
import edu.uark.uarkregisterapp.models.api.interfaces.LoadFromJsonInterface;
import edu.uark.uarkregisterapp.models.transition.EmployeeTransition;

public class Employee implements ConvertToJsonInterface, LoadFromJsonInterface<Employee> {
	/*List of variables present:
		UUID record_id
		String first_name
		String last_name
		String employee_id
		String active
		String title
		String manager
		String password
	*/	

	private UUID id;
	public UUID getId() {
		return this.id;
	}

	public Employee setId(UUID id) {
		this.id = id;
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

	private Date created;
	public Date getCreated() {
		return this.created;
	}

	public Employee setCreated(Date created) {
		this.created = created;
		return this;
	}


	//TODO: Figure out what to do about EmployeeFieldName EmployeeListingFieldName
	//	Because this methods need those.
	
	@Override
	public Employee loadFromJson(JSONObject rawJsonObject) {
		String value = rawJsonObject.optString(EmployeeFieldName.ID.getFieldName());

		System.out.println(value);
		if (!StringUtils.isBlank(value)) {
			System.out.println(value);
			this.id = UUID.fromString(value);

		}
		this.first_name = rawJsonObject.optString(EmployeeFieldName.FIRST_NAME.getFieldName());
		this.last_name = rawJsonObject.optString(EmployeeFieldName.LAST_NAME.getFieldName());
		this.employee_id = rawJsonObject.optString(EmployeeFieldName.EMPLOYEE_ID.getFieldName());
		this.active = rawJsonObject.optString(EmployeeFieldName.ACTIVE.getFieldName());
		this.title = rawJsonObject.optString(EmployeeFieldName.TITLE.getFieldName());
		this.manager = rawJsonObject.optString(EmployeeFieldName.MANAGER.getFieldName());
		this.password = rawJsonObject.optString(EmployeeFieldName.PASSWORD.getFieldName());

		value = rawJsonObject.optString(EmployeeFieldName.CREATED.getFieldName());
		if (!StringUtils.isBlank(value)) {
			try {
				this.created = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.US).parse(value);
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
			jsonObject.put(EmployeeFieldName.ID.getFieldName(), this.id.toString());
			jsonObject.put(EmployeeFieldName.FIRST_NAME.getFieldName(), this.first_name);
			jsonObject.put(EmployeeFieldName.LAST_NAME.getFieldName(), this.last_name);
			jsonObject.put(EmployeeFieldName.EMPLOYEE_ID.getFieldName(), this.employee_id);
			jsonObject.put(EmployeeFieldName.ACTIVE.getFieldName(), this.active);
			jsonObject.put(EmployeeFieldName.TITLE.getFieldName(), this.title);
			jsonObject.put(EmployeeFieldName.MANAGER.getFieldName(), this.manager);
			jsonObject.put(EmployeeFieldName.PASSWORD.getFieldName(), this.password);
			jsonObject.put(EmployeeFieldName.CREATED.getFieldName(), (new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.US)).format(this.created));
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return jsonObject;
	}
	

	public Employee() {
		this.id = new UUID(0, 0);
		this.first_name = "";
		this.last_name = "";
		this.employee_id = "";
		this.active = "";
		this.title = "";
		this.manager = "";
		this.password = "";
		this.created = new Date();
	}

	public Employee(EmployeeTransition employeeTransition) {
		this.id = employeeTransition.getId();
		this.first_name = employeeTransition.getFirst_Name();
		this.last_name = employeeTransition.getLast_Name();
		this.employee_id = employeeTransition.getEmployee_Id();
		this.active = employeeTransition.getActive();
		this.title = employeeTransition.getTitle();
		this.manager = employeeTransition.getManager();
		this.password = employeeTransition.getPassword();
		this.created = employeeTransition.getCreated();
	}
}































//
