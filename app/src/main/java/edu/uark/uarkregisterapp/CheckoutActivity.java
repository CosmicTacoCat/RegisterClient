package edu.uark.uarkregisterapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import edu.uark.uarkregisterapp.adapters.TransactionListAdapter;
import edu.uark.uarkregisterapp.models.api.ApiResponse;
import edu.uark.uarkregisterapp.models.api.Product;
import edu.uark.uarkregisterapp.models.transition.EmployeeTransition;
import edu.uark.uarkregisterapp.models.api.Cart;
import edu.uark.uarkregisterapp.models.api.services.ProductService;
import edu.uark.uarkregisterapp.R;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CheckoutActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        this.employeeTransition = this.getIntent().getParcelableExtra(this.getString(R.string.intent_extra_employee));
        //this.cartname = this.getIntent().getStringArrayListExtra("cartlistname");
        //this.cartquantity = this.getIntent().getStringArrayListExtra("carltlistquantity");
        this.cartcontents = (ArrayList<Cart>)this.getIntent().getSerializableExtra("cartcontents");

        employee_logged_in = this.employeeTransition.getFirst_Name() + " " + this.employeeTransition.getLast_Name();


        System.out.println(employee_logged_in);
        System.out.println(cartcontents.size());
        ActionBar actionBar = this.getSupportActionBar();
        if (actionBar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//
//        (new CheckoutActivity.RetrieveProductsTask()).execute();
//    }

    private ListView getProductsListView() {
        return (ListView) this.findViewById(R.id.list_view_products);
    }

    public void BackOnClick(View view) {
//        this.startActivity(new Intent(getApplicationContext(), TransactionActivity.class));
    finish();
    }
    public void FinalCheckoutOnClick(View view) {
//        this.startActivity(new Intent(getApplicationContext(), TransactionActivity.class));
        finish();
    }
    private class RetrieveProductsTask extends AsyncTask<Void, Void, ApiResponse<List<Product>>> {
        @Override
        protected void onPreExecute() {
            this.loadingProductsAlert.show();
        }

        @Override
        protected ApiResponse<List<Product>> doInBackground(Void... params) {
            ApiResponse<List<Product>> apiResponse = (new ProductService()).getProducts();

            if (apiResponse.isValidResponse()) {
                products.clear();
                products.addAll(apiResponse.getData());
            }

            return apiResponse;
        }

        @Override
        protected void onPostExecute(ApiResponse<List<Product>> apiResponse) {
            if (apiResponse.isValidResponse()) {
                transactionListAdapter.notifyDataSetChanged();
            }

            this.loadingProductsAlert.dismiss();

            if (!apiResponse.isValidResponse()) {
                new AlertDialog.Builder(CheckoutActivity.this).
                        setMessage(R.string.alert_dialog_products_load_failure).
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

        private AlertDialog loadingProductsAlert;

        private RetrieveProductsTask() {
            this.loadingProductsAlert = new AlertDialog.Builder(CheckoutActivity.this).
                    setMessage(R.string.alert_dialog_products_loading).
                    create();
        }
    }


    private EmployeeTransition employeeTransition;
    public String employee_logged_in;
    private List<Product> products;
    private ArrayList<Cart> cartcontents;
    private ArrayList<String> cartname;
    private ArrayList<Integer> cartquantity;
    private TransactionListAdapter transactionListAdapter;
}

