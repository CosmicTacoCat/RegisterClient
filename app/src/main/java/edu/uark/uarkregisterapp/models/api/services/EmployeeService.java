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
import edu.uark.uarkregisterapp.models.api.enums.ProductApiMethod;
import edu.uark.uarkregisterapp.models.api.interfaces.PathElementInterface;

public class EmployeeService extends BaseRemoteService {

    //POSTs the employee id and password to the server and receives its response, which should contain a json.
    // Then the employee's details are read from that response.
    //I'm not sure if this response from the server will contain the employee's data.
    public ApiResponse<Employee> loginEmployee(Employee employee) {
        return this.readEmployeeDetailsFromResponse(
                this.<Employee>performPostRequest(
                        this.buildPath(), employee.convertToJson()
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

        return apiResponse;
    }

}
