package edu.uark.uarkregisterapp;

import android.content.Intent;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

import edu.uark.uarkregisterapp.models.api.ApiResponse;
import edu.uark.uarkregisterapp.models.api.Employee;
import edu.uark.uarkregisterapp.models.api.services.EmployeeService;
import edu.uark.uarkregisterapp.models.transition.EmployeeTransition;


// this activity is currently configured to be launched from the main activity.
public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        ActionBar actionBar = this.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        this.employees = new ArrayList<>();
        this.employeeTransition = new EmployeeTransition();
        (new existenceCheckTask()).execute();
    }


    public void loginButtonOnClick(View view) {
        if (!validateInput()) { return; }

        (new employeeLoginTask()).execute();
    }



    //TODO: disable this function and button for release
    public void devSkipLogin(View view) {
        Intent intent = new Intent(getApplicationContext(), MainMenuActivity.class);
        intent.putExtra(
                getString(R.string.intent_extra_employee),
                new EmployeeTransition()
        );
        this.startActivity(intent);
    }


    public void devSkipLogin2(View view) {
        Intent intent = new Intent(getApplicationContext(), CreateEmployeeActivity.class);
        this.startActivity(intent);
    }





    private EditText getEmployeeIDEditText() {
        return (EditText) this.findViewById(R.id.edit_text_login);
    }

    private EditText getEmployeePasswordEditText() {
        return (EditText) this.findViewById(R.id.edit_text_password);
    }

    private boolean validateInput() {
        boolean inputIsValid = true;
        boolean blankPassword = false;
        String validationMessage = StringUtils.EMPTY;

        if (StringUtils.isBlank(this.getEmployeeIDEditText().getText().toString())) {
            validationMessage = "Employee ID cannot be blank.";
            inputIsValid = false;
        }

        if (StringUtils.isBlank(this.getEmployeePasswordEditText().getText().toString())) {
            validationMessage = "Password cannot be blank.";
            inputIsValid = false;
            blankPassword = true;
        }

        if (!inputIsValid && blankPassword) {
            validationMessage = "Employee ID and password cannot be blank.";
        }

        if (!inputIsValid) {
            new AlertDialog.Builder(this).
                    setMessage(validationMessage).
                    setPositiveButton(R.string.button_dismiss,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.dismiss();
                                }
                            }).
                    create().
                    show();
        }

        return inputIsValid;
    }

    //TODO: Make sure this class is extending the AsyncTask class properly for login.
    private class employeeLoginTask extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected void onPreExecute() {
            this.employeeLoginAlert.show();
        }


        //TODO: make sure Employee, EmployeeTransition and EmployeeService classes have been created.
        //TODO: set this up for employee login usage.
        @Override
        protected Boolean doInBackground(Void... params) {
            Employee employee = (new Employee()).
                    setEmployee_Id(getEmployeeIDEditText().getText().toString()).
                    setPassword(getEmployeePasswordEditText().getText().toString());

            ApiResponse<Employee> apiResponse = new EmployeeService().EmployeeLogin(employee);


            if (apiResponse.isValidResponse()) {
                //currently assuming the server will send the employee data in this apiresponse.
                employeeTransition.setEmployee_Id(apiResponse.getData().getEmployee_Id());
                employeeTransition.setFirst_Name(apiResponse.getData().getFirst_Name());
                employeeTransition.setLast_Name(apiResponse.getData().getLast_Name());
                employeeTransition.setActive(apiResponse.getData().getActive());
                employeeTransition.setTitle(apiResponse.getData().getTitle());
                employeeTransition.setManager(apiResponse.getData().getManager());
                employeeTransition.setPassword(apiResponse.getData().getPassword());
                employeeTransition.setCreated(apiResponse.getData().getCreated()); //Wait for push.
            }

            //TODO: perhaps return a value representing whether or not the server allowed login. apiResponse.getdata().getValidLogin() ?
            return apiResponse.isValidResponse();
        }

        //Successful login sends employee data to main menu activity and kills this activity.
        @Override
        protected void onPostExecute(Boolean successfulLogin) {
            String message;
            employeeLoginAlert.dismiss();


            if (successfulLogin) {
                Intent intent = new Intent(getApplicationContext(), MainMenuActivity.class);
                intent.putExtra( getString(R.string.intent_extra_employee), employeeTransition);
                startActivity(intent);
                finish();
            }
            else {

                message = "login FAIL";
                new AlertDialog.Builder(LoginActivity.this).
                        setMessage(message).
                        setPositiveButton(
                                R.string.button_dismiss,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.dismiss();
                                    }
                                }
                        ).
                        create().
                        show();
            }

        }

        private AlertDialog employeeLoginAlert;

        private employeeLoginTask() {
            this.employeeLoginAlert = new AlertDialog.Builder(LoginActivity.this).
                    setMessage(R.string.alert_dialog_employee_login).
                    create();
        }
    }



    private class existenceCheckTask extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected void onPreExecute() { }


        //TODO: make sure Employee, EmployeeTransition and EmployeeService classes have been created.
        //TODO: set this up for employee login usage.
        @Override
        protected Boolean doInBackground(Void... params) {
            ApiResponse<List<Employee>> apiResponse = (new EmployeeService()).checkEmployeeExistence();

            employees.clear();
            employees.addAll(apiResponse.getData());

            boolean existence = false;
            if (employees.isEmpty()) {existence = true;}
            return existence;
        }

        //Successful login sends employee data to main menu activity and kills this activity.
        @Override
        protected void onPostExecute(Boolean existence) {
            if (existence) {
                Intent intent = new Intent(getApplicationContext(), CreateEmployeeActivity.class);
                startActivity(intent);
                finish();
            }
        }

        private existenceCheckTask() { }
    }

    private EmployeeTransition employeeTransition;
    private List<Employee> employees;
}
