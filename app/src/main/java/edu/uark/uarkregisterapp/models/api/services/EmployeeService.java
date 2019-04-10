package edu.uark.uarkregisterapp.models.api.services;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import edu.uark.uarkregisterapp.models.api.ApiResponse;
import edu.uark.uarkregisterapp.models.api.Employee;
import edu.uark.uarkregisterapp.models.api.enums.ApiObject;
import edu.uark.uarkregisterapp.models.api.enums.EmployeeApiMethod;
import edu.uark.uarkregisterapp.models.api.interfaces.PathElementInterface;

public class EmployeeService extends BaseRemoteService {

    public ApiResponse<Employee> createEmployee(Employee employee) {
        return this.readEmployeeDetailsFromResponse(
                this.<Employee>performPostRequest(
                        this.buildPath(
                                (new PathElementInterface[]{EmployeeApiMethod.CREATE}), "")
                        , employee.convertToJson()
                )
        );
    }


    public ApiResponse<Employee> getEmployee(String employee_id) {
        return this.readEmployeeDetailsFromResponse(
                this.<Employee>performGetRequest(
                        this.buildPath(employee_id)
                )
        );
    }

    //getEmployees method here

    //check employee existence method here
    public ApiResponse<List<Employee>> checkEmployeeExistence() {
        ApiResponse<List<Employee>> apiResponse = this.performGetRequest(
                this.buildPath()
        );

        JSONArray rawJsonArray = this.rawResponseToJSONArray(apiResponse.getRawResponse());

        if (rawJsonArray != null) {

            ArrayList<Employee> employees = new ArrayList<>(rawJsonArray.length());

            for (int i = 0; i < rawJsonArray.length(); i++) {
                try {
                    employees.add((new Employee()).loadFromJson(rawJsonArray.getJSONObject(i)));
                } catch (JSONException e) {
                    Log.d("GET EMPLOYEES", e.getMessage());
                }
            }

            apiResponse.setData(employees);
        }

        else {
            apiResponse.setData(new ArrayList<Employee>(0));
        }

        return apiResponse;
    }








    //POSTs the employee id and password to the server and receives its response, which should contain a json.
    // Then the employee's details are read from that response.
    //I'm not sure if this response from the server will contain the employee's data.
    public ApiResponse<Employee> EmployeeLogin(Employee employee) {
        return this.readEmployeeDetailsFromResponse(
                this.<Employee>performPostRequest(
                        this.buildPath(
                                (new PathElementInterface[]{EmployeeApiMethod.LOGIN}), "")
                        , employee.convertToJson()
                )
        );
    }


    public ApiResponse<Employee> readEmployeeDetailsFromResponse(ApiResponse<Employee> apiResponse) {
        JSONObject rawJsonObject = this.rawResponseToJSONObject(
                apiResponse.getRawResponse()
        );

        if (rawJsonObject != null) {
            apiResponse.setData(
                    (new Employee()).loadFromJson(rawJsonObject)
            );
        }
        return apiResponse;
    }

    public EmployeeService() {super(ApiObject.EMPLOYEE);}
}
