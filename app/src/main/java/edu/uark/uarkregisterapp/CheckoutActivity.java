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
import android.widget.TextView;

import org.json.JSONObject;

import edu.uark.uarkregisterapp.adapters.TransactionListAdapter;
import edu.uark.uarkregisterapp.adapters.CheckoutListAdapter;
import edu.uark.uarkregisterapp.models.api.ApiResponse;
import edu.uark.uarkregisterapp.models.api.Product;
import edu.uark.uarkregisterapp.models.transition.EmployeeTransition;
import edu.uark.uarkregisterapp.models.api.Cart;
import edu.uark.uarkregisterapp.models.api.services.ProductService;
import edu.uark.uarkregisterapp.models.api.services.CartService;
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
        this.cartcontents = (ArrayList<Cart>)this.getIntent().getSerializableExtra("cartcontents");
        this.checkoutListAdapter = new CheckoutListAdapter (this, this.cartcontents);
       this.getCartListView().setAdapter(this.checkoutListAdapter);
        this.employeeTransition = this.getIntent().getParcelableExtra(this.getString(R.string.intent_extra_employee));
        for (int i = 0; i < cartcontents.size(); i++)
        {
            System.out.println(cartcontents.get(i).getLookupCode());
        }
        ActionBar actionBar = this.getSupportActionBar();
        if (actionBar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }



    private ListView getCartListView() {
        return (ListView) this.findViewById(R.id.list_view_cart);
    }


    public void BackOnClick(View view) {
//        this.startActivity(new Intent(getApplicationContext(), TransactionActivity.class));
    finish();
    }
    public void FinalCheckoutOnClick(View view) {
        (new CheckoutActivity.SaveCartTask()).execute();

     // finish();
    }
    private class SaveCartTask extends AsyncTask<Void, Void, ApiResponse<List<Cart>>> {
       @Override
        protected void onPreExecute() {
            loadingCartAlert = new AlertDialog.Builder(CheckoutActivity.this).
                    setMessage("Saving cart...").
                    create();
            this.loadingCartAlert.show();
        }

        @Override
        protected ApiResponse<List<Cart>> doInBackground(Void... params) {
            ApiResponse<List<Cart>> apiResponse = (new CartService()).getCart();

            if (apiResponse.isValidResponse()) {
                cartcontents.clear();
                cartcontents.addAll(apiResponse.getData());
            }

            return apiResponse;
        }


        protected void onPostExecute(ApiResponse<List<Cart>> apiResponse) {
            if (apiResponse.isValidResponse()) {
                checkoutListAdapter.notifyDataSetChanged();
            }

            this.loadingCartAlert.dismiss();

            if (!apiResponse.isValidResponse()) {
                new AlertDialog.Builder(CheckoutActivity.this).
                        setMessage(R.string.alert_dialog_cart_load_failure).
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
            finish();
        }




        private AlertDialog loadingCartAlert;

        public String getcartcontents(int i) {
                return cartcontents.get(i).getLookupCode();
        }

        public Integer getcartcount(int i) {
            return cartcontents.get(i).getQuantity();
        }


    private SaveCartTask() {
        this.savingCartAlert = new AlertDialog.Builder(CheckoutActivity.this).
                setMessage("Saving cart...").
                create();
    }
        private AlertDialog savingCartAlert;
    }

    private EmployeeTransition employeeTransition;
    public String employee_logged_in;
    private List<Product> products;
    private ArrayList<Cart> cartcontents;
    private List<String> list;
    private String cartname;
    private Integer cartquantity;
   private CheckoutListAdapter checkoutListAdapter;
    private TransactionListAdapter transactionListAdapter;
}

