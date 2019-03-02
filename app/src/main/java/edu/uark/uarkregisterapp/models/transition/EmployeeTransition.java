package edu.uark.uarkregisterapp.models.transition;

import android.os.Parcel;
import android.os.Parcelable;

import org.apache.commons.lang3.StringUtils;

//import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

import edu.uark.uarkregisterapp.commands.converters.ByteToUUIDConverterCommand;
import edu.uark.uarkregisterapp.commands.converters.UUIDToByteConverterCommand;
import edu.uark.uarkregisterapp.models.api.Employee;

public class EmployeeTransition implements Parcelable {
	private UUID record_id;
	public UUID getId() {
		return this.record_id;
	}

	public EmployeeTransition setId(UUID record_id) {
		this.record_id = record_id;
		return this;
	}


	private String first_name;
	public String getFirst_Name() {
		return this.first_name;
	}

	public EmployeeTransition setFirst_Name(String first_name) {
		this.first_name = first_name;
		return this;
	}


	private String last_name;
	public String getLast_Name() {
		return this.last_name;
	}

	public EmployeeTransition setLast_Name(String last_name) {
		this.last_name = last_name;
		return this;
	}


	private String employee_id;
	public String getEmployee_Id() {
		return this.employee_id;
	}

	public EmployeeTransition setEmployee_Id(String employee_id) {
		this.employee_id = employee_id;
		return this;
	}


	private String active;
	public String getActive() {
		return this.active;
	}

	public EmployeeTransition setActive(String active) {
		this.active = active;
		return this;
	}


	private String title;
	public String getTitle() {
		return this.title;
	}

	public EmployeeTransition setTitle(String title) {
		this.title = title;
		return this;
	}


	private String manager;
	public String getManager() {
		return this.manager;
	}

	public EmployeeTransition setManager(String manager) {
		this.manager = manager;
		return this;
	}


	private String password;
	public String getPassword() {
		return this.password;
	}
	
	public EmployeeTransition setPassword(String password) {
		this.password = password;
		return this;
	}

	private Date created;
	public Date getCreated() {
		return this.created;
	}

	public EmployeeTransition setCreated(Date created) {
		this.created = created;
		return this;
	}

	//TODO: Convert this to be used with EmployeeTransition.

	@Override
	public void writeToParcel(Parcel destination, int flags) {
		destination.writeByteArray((new UUIDToByteConverterCommand()).setValueToConvert(this.record_id).execute());
		destination.writeString(this.employee_id);
		destination.writeLong(this.created.getTime());
	}

	@Override
	public int describeContents() {
		return 0;
	}

	public static final Parcelable.Creator<EmployeeTransition> CREATOR = new Parcelable.Creator<EmployeeTransition>() {
		public EmployeeTransition createFromParcel(Parcel employeeTransitionParcel) {
			return new EmployeeTransition(employeeTransitionParcel);
		}

		public EmployeeTransition[] newArray(int size) {
			return new EmployeeTransition[size];
		}
	};



// Constructors ////
	public EmployeeTransition() {
		this.record_id = new UUID(0, 0);
		this.first_name = "";
		this.last_name = "";
		this.employee_id = "";
		this.active = "";
		this.title = "";
		this.manager = "";
		this.password = "";
		this.created = new Date();
	}

	public EmployeeTransition(Employee employee) {
		this.record_id = employee.getId();
		this.first_name = employee.getFirst_Name();
		this.last_name = employee.getLast_Name();
		this.employee_id = employee.getEmployee_Id();
		this.active = employee.getActive();
		this.title = employee.getTitle();
		this.manager = employee.getManager();
		this.password = employee.getPassword();
		this.created = employee.getCreated();
	}

	private EmployeeTransition(Parcel EmployeeTransitionParcel) {

		//TODO: set this up.
		this.record_id = (new ByteToUUIDConverterCommand()).setValueToConvert(EmployeeTransitionParcel.createByteArray()).execute();
		this.first_name = EmployeeTransitionParcel.readString();
		this.last_name = EmployeeTransitionParcel.readString();
		this.employee_id = EmployeeTransitionParcel.readString();
		this.active = EmployeeTransitionParcel.readString();
		this.title = EmployeeTransitionParcel.readString();
		this.manager = EmployeeTransitionParcel.readString();
		this.password = EmployeeTransitionParcel.readString();
		this.created = new Date();
		this.created.setTime(EmployeeTransitionParcel.readLong());
	}
}
