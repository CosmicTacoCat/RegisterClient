package edu.uark.uarkregisterapp;


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

//import local packages. For example, EmployeeTransition

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

        //TODO: possibly get an employee transistion intent here.
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
        if (!validateInput()) {
            return;
        }

        (new employeeLoginTask()).execute();
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

    private class employeeLoginTask extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected void onPreExecute() {
            this.employeeLoginAlert.show();
        }


        //TODO: make sure Employee, EmployeeTransition and EmployeeService classes have been created.
        //TODO: set this up for employee login usage.
        /*@Override
        protected Boolean doInBackground(Void... params) {
            Employee employee = (new Employee()).
                    setId(employeeTransition.getId()).
                    setLookupCode(getProductLookupCodeEditText().getText().toString()).
                    setCount(Integer.parseInt(getProductCountEditText().getText().toString()));

            ApiResponse<Product> apiResponse = (
                    (product.getId().equals(new UUID(0, 0)))
                            ? (new ProductService()).createProduct(product)
                            : (new ProductService()).updateProduct(product)
            );

            if (apiResponse.isValidResponse()) {
                productTransition.setCount(apiResponse.getData().getCount());
                productTransition.setLookupCode(apiResponse.getData().getLookupCode());
            }

            return apiResponse.isValidResponse();
        }*/


				// Remove this block after the employee classes are finished
        @Override
        protected Boolean doInBackground(Void... params) {
            return false;
        }

        @Override
        protected void onPostExecute(Boolean successfulLogin) {
            String message;

            employeeLoginAlert.dismiss();

            if (successfulLogin) { 
								message = "sampletext"; // Remove this line after the employee classes are finished
                //TODO: finish activity. Send an intent with employee info.
                // If this activity was launched by another activity, send back the intent and make sure this activity was launched
                // using the startActivityForResult() method rather than startActivity().
            } else {
                message = getString(R.string.alert_dialog_employee_login_failure);
                }

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

        private AlertDialog employeeLoginAlert;

        private employeeLoginTask() {
            this.employeeLoginAlert = new AlertDialog.Builder(LoginActivity.this).
                    setMessage(R.string.alert_dialog_employee_login).
                    create();
        }
    }

    //private EmployeeTransition employeeTransition;
}
