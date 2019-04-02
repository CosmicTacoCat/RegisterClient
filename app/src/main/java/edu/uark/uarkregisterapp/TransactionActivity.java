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


public class TransactionActivity extends AppCompatActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

     //   ActionBar actionBar = this.getSupportActionBar();
     //   if (actionBar != null) {
     //       actionBar.setDisplayHomeAsUpEnabled(true);
     //   }

      /*  this.employees = new ArrayList<>();
        this.employeeTransition = new EmployeeTransition();
        (new LoginActivity.existenceCheckTask()).execute();*/
    }


}
