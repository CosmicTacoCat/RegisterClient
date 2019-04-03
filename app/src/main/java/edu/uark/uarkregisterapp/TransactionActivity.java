package edu.uark.uarkregisterapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;


public class TransactionActivity extends AppCompatActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_);
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
