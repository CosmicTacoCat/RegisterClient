package edu.uark.uarkregisterapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
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

}
