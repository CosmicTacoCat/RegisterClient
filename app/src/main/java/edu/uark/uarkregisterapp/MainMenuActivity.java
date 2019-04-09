package edu.uark.uarkregisterapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import edu.uark.uarkregisterapp.models.transition.EmployeeTransition;
import edu.uark.uarkregisterapp.models.transition.ProductTransition;

public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        this.employeeTransition = this.getIntent().getParcelableExtra(this.getString(R.string.intent_extra_employee));

        //Welcome Message
        employee_logged_in = this.employeeTransition.getFirst_Name() + " " + this.employeeTransition.getLast_Name();
        employee_welcome = "Welcome " + employee_logged_in + "! What would you like to do next?";
        TextView textView = (TextView) findViewById(R.id.textView2);
        textView.setText(employee_welcome);
    }


    public void NoFunctionalityButtonOnClick(View view) {
        new AlertDialog.Builder(this).
                setMessage("This functionality has not yet been implemented").
                setNegativeButton(
                        R.string.button_ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        }
                ).
                create().
                show();
    }

    public void LogOutOnClick(View view) {
       this.startActivity(new Intent(getApplicationContext(), LoginActivity.class));
    }

    public void TransactionOnClick(View view) {
        this.startActivity(new Intent(getApplicationContext(), TransactionActivity.class));
    }


    //public void ReturnsOnClick(View view) {
       // this.startActivity(new Intent(getApplicationContext(), ReturnsActivity.class));
    //}

    private EmployeeTransition employeeTransition;

    public String employee_logged_in;
    String employee_welcome;
}
