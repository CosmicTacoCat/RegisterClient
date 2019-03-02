package edu.uark.uarkregisterapp;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

import edu.uark.uarkregisterapp.models.api.ApiResponse;
import edu.uark.uarkregisterapp.models.api.Product;
import edu.uark.uarkregisterapp.models.api.services.ProductService;
import edu.uark.uarkregisterapp.models.transition.ProductTransition;

public class CreateEmployeeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_employee);
    }

    public void saveButtonOnClick(View view) {
        if (!this.validateInput()) {
            return;
        }

        (new CreateEmployeeActivity.SaveEmployeeTask()).execute();
    }

    private EditText getEmployeeFirstNameEditText() {
        return (EditText) this.findViewById(R.id.edit_text_employee_first_name);
    }

    private EditText getEmployeeLastNameEditText() {
        return (EditText) this.findViewById(R.id.edit_text_employee_last_name);
    }

    private EditText getEmployeePasswordEditText() {
        return (EditText) this.findViewById(R.id.edit_text_employee_password);
    }

    private boolean validateInput() {
        boolean inputIsValid = true;
        String validationMessage = StringUtils.EMPTY;

        if (StringUtils.isBlank(this.getEmployeeFirstNameEditText().getText().toString())) {
            validationMessage = "First Name can not be empty";
            inputIsValid = false;
        }

        if (StringUtils.isBlank(this.getEmployeeLastNameEditText().getText().toString())) {
            validationMessage = "Last Name can not be empty";
            inputIsValid = false;
        }

        if (StringUtils.isBlank(this.getEmployeePasswordEditText().getText().toString())) {
            validationMessage = "Password can not be empty";
            inputIsValid = false;
        }

        if (!inputIsValid) {
            new AlertDialog.Builder(this).
                    setMessage(validationMessage).
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

        return inputIsValid;
    }

    private class SaveEmployeeTask extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected void onPreExecute() {
            this.savingEmployeeAlert.show();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            Employee employee = (new Employee()).
                    setFirstName(getEmployeeFirstNameEditText().getText().toString()).
                    setLookupCode(getEmployeeLastNameEditText().getText().toString()).
                    setCount(getEmployeePasswordEditText().getText().toString()));

            ApiResponse<Employee> apiResponse = (
                    (employee.getId().equals(new UUID(0, 0)))
                            ? (new EmployeeService()).createEmployee(employee)
                            : (new EmployeeService()).updateEmployee(employee)
            );

            if (apiResponse.isValidResponse()) {
                employeeTransition.setFirstName(apiResponse.getData().getFirstName());
                employeeTransition.setLastName(apiResponse.getData().getLastName());
                employeeTransition.setPassword(apiResponse.getData().getPassword());
            }

            return apiResponse.isValidResponse();
        }

        @Override
        protected void onPostExecute(Boolean successfulSave) {
            String message;

            savingEmployeeAlert.dismiss();

            if (successfulSave) {
                message = "Employee Save Successful";
            } else {
                message = "Employee Save Failure";
            }

            new AlertDialog.Builder(CreateEmployeeActivity.this).
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

        private AlertDialog savingEmployeeAlert;

        private SaveEmployeeTask() {
            this.savingEmployeeAlert = new AlertDialog.Builder(CreateEmployeeActivity.this).
                    setMessage("Saving Product...").
                    create();
        }
    }
    private EmployeeTransition employeeTransition;
}
