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

            this.employeeTransition = new EmployeeTransition();
        }



        //TODO: Query the server to see if an initial employee must be created first.
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:  // Respond to the action bar's Up/Home button
                this.finish();

                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // This button in this activity's xml file specifies a function to call when clicked.
    // That seems to prevent the need to manually create a button object and register it with an on-click listener.
    public void loginButtonOnClick(View view) {
        if (!validateInput()) { return; }

        (new employeeLoginTask()).execute();
    }

    //TODO: disable this function and button for release
    public void devSkipLogin(View view) {
        //this.startActivity(new Intent(getApplicationContext(), MainMenuActivity.class));
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

            //TODO: Make EmployeeService. In that, make loginEmployee() in place of createProduct(). See ProductService for reference.
            ApiResponse<Employee> apiResponse = new EmployeeService().loginEmployee(employee);
            if (apiResponse.isValidResponse()) {
                //currently assuming the server will send the employee data in this apiresponse.
                employeeTransition.setEmployee_Id(apiResponse.getData().getEmployee_Id());
                employeeTransition.setFirst_Name(apiResponse.getData().getFirst_Name());
                //TODO: Add finish setting the rest of the employeeTransition's data.
            }

            //TODO: perhaps return a value representing whether or not the server allowed login.
            return apiResponse.isValidResponse();
        }

        @Override
        protected void onPostExecute(Boolean successfulSave) {
            if (successfulSave) {
                Intent intent = new Intent(getApplicationContext(), MainMenuActivity.class);

                intent.putExtra( getString(R.string.intent_extra_employee), employeeTransition);
            }
        }

        private AlertDialog employeeLoginAlert;

        private employeeLoginTask() {
            this.employeeLoginAlert = new AlertDialog.Builder(LoginActivity.this).
                    setMessage(R.string.alert_dialog_employee_login).
                    create();
        }
    }

    private EmployeeTransition employeeTransition;
}
